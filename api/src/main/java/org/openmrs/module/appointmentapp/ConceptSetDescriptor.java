package org.openmrs.module.appointmentapp;

import org.apache.commons.beanutils.PropertyUtils;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.util.OpenmrsUtil;

import java.util.Iterator;

/**
 * @author Stanslaus Odhaimbo
 *         Created on 23/03/2017.
 */
public abstract class ConceptSetDescriptor {
    public ConceptSetDescriptor() {
    }

    protected void setup(ConceptService conceptService, String conceptSourceName, ConceptSetDescriptorField primaryConceptField, ConceptSetDescriptorField... memberConceptFields) {
        try {
            String ex = primaryConceptField.getConceptCode();
            Concept primaryConcept = conceptService.getConceptByMapping(ex, conceptSourceName);
            if(primaryConcept == null) {
                throw new IllegalStateException("Couldn\'t find primary concept for " + this.getClass().getSimpleName() + " which should be mapped as " + conceptSourceName + ":" + ex);
            } else {
                PropertyUtils.setProperty(this, primaryConceptField.getName(), primaryConcept);
                ConceptSetDescriptorField[] arr$ = memberConceptFields;
                int len$ = memberConceptFields.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    ConceptSetDescriptorField conceptSetDescriptorField = arr$[i$];
                    String propertyName = conceptSetDescriptorField.getName();
                    String mappingCode = conceptSetDescriptorField.getConceptCode();
                    Concept childConcept = conceptService.getConceptByMapping(mappingCode, conceptSourceName);
                    if(conceptSetDescriptorField.isRequired()) {
                        if(childConcept == null) {
                            throw new IllegalStateException("Couldn\'t find " + propertyName + " concept for " + this.getClass().getSimpleName() + " which should be mapped as " + conceptSourceName + ":" + mappingCode);
                        }

                        if(!primaryConcept.getSetMembers().contains(childConcept)) {
                            throw new IllegalStateException("Concept mapped as " + conceptSourceName + ":" + mappingCode + " needs to be a set member of concept " + primaryConcept.getConceptId() + " which is mapped as " + conceptSourceName + ":" + ex);
                        }
                    }

                    PropertyUtils.setProperty(this, propertyName, childConcept);
                }

            }
        } catch (Exception var14) {
            if(var14 instanceof RuntimeException) {
                throw (RuntimeException)var14;
            } else {
                throw new IllegalStateException(var14);
            }
        }
    }

    protected Obs findMember(Obs obsGroup, Concept concept) {
        Iterator i$ = obsGroup.getGroupMembers(false).iterator();

        Obs candidate;
        do {
            if(!i$.hasNext()) {
                return null;
            }

            candidate = (Obs)i$.next();
        } while(!candidate.getConcept().equals(concept));

        return candidate;
    }

    protected void setCodedOrFreeTextMember(Obs obsGroup, CodedOrFreeTextAnswer answer, Concept questionIfCoded, Concept questionIfNonCoded) {
        if(answer.getNonCodedAnswer() != null) {
            this.setFreeTextMember(obsGroup, questionIfNonCoded, answer.getNonCodedAnswer());
            this.setCodedMember(obsGroup, questionIfCoded, (Concept)null, (ConceptName)null);
        } else {
            this.setFreeTextMember(obsGroup, questionIfNonCoded, (String)null);
            this.setCodedMember(obsGroup, questionIfCoded, answer.getCodedAnswer(), answer.getSpecificCodedAnswer());
        }

    }

    private void setFreeTextMember(Obs obsGroup, Concept memberConcept, String memberAnswer) {
        Obs member = this.findMember(obsGroup, memberConcept);
        boolean needToVoid = member != null && !OpenmrsUtil.nullSafeEquals(memberAnswer, member.getValueText());
        boolean needToCreate = memberAnswer != null && (member == null || needToVoid);
        if(needToVoid) {
            member.setVoided(Boolean.valueOf(true));
            member.setVoidReason(this.getDefaultVoidReason());
        }

        if(needToCreate) {
            this.addToObsGroup(obsGroup, this.buildObsFor(memberConcept, memberAnswer));
        }

    }

    protected String getDefaultVoidReason() {
        return this.getClass().getSimpleName() + " modifying obs group";
    }

    protected void setCodedMember(Obs obsGroup, Concept memberConcept, Concept memberAnswer, ConceptName specificAnswer) {
        Obs member = this.findMember(obsGroup, memberConcept);
        boolean needToVoid = member != null && (!memberAnswer.equals(member.getValueCoded()) || !OpenmrsUtil.nullSafeEquals(specificAnswer, member.getValueCodedName()));
        boolean needToCreate = memberAnswer != null && (member == null || needToVoid);
        if(needToVoid) {
            member.setVoided(Boolean.valueOf(true));
            member.setVoidReason(this.getDefaultVoidReason());
        }

        if(needToCreate) {
            this.addToObsGroup(obsGroup, this.buildObsFor(memberConcept, memberAnswer, specificAnswer));
        }

    }

    private void addToObsGroup(Obs obsGroup, Obs member) {
        member.setPerson(obsGroup.getPerson());
        member.setObsDatetime(obsGroup.getObsDatetime());
        member.setLocation(obsGroup.getLocation());
        member.setEncounter(obsGroup.getEncounter());
        obsGroup.addGroupMember(member);
    }

    protected Obs buildObsFor(Concept question, String answer) {
        Obs obs = new Obs();
        obs.setConcept(question);
        obs.setValueText(answer);
        return obs;
    }

    protected Obs buildObsFor(Concept question, Concept answer, ConceptName answerName) {
        Obs obs = new Obs();
        obs.setConcept(question);
        obs.setValueCoded(answer);
        obs.setValueCodedName(answerName);
        return obs;
    }

    protected Obs buildObsFor(CodedOrFreeTextAnswer codedOrFreeTextAnswer, Concept questionIfCoded, Concept questionIfNonCoded) {
        Obs obs = new Obs();
        if(codedOrFreeTextAnswer.getNonCodedAnswer() != null) {
            obs.setConcept(questionIfNonCoded);
            obs.setValueText(codedOrFreeTextAnswer.getNonCodedAnswer());
        } else {
            obs.setConcept(questionIfCoded);
            obs.setValueCoded(codedOrFreeTextAnswer.getCodedAnswer());
            obs.setValueCodedName(codedOrFreeTextAnswer.getSpecificCodedAnswer());
        }

        return obs;
    }

    protected Concept findAnswer(Concept concept, String codeForAnswer) {
        Iterator i$ = concept.getAnswers().iterator();

        Concept answerConcept;
        do {
            if(!i$.hasNext()) {
                throw new IllegalStateException("Cannot find answer mapped with org.openmrs.module.emrapi:" + codeForAnswer + " in the concept " + concept.getName());
            }

            ConceptAnswer conceptAnswer = (ConceptAnswer)i$.next();
            answerConcept = conceptAnswer.getAnswerConcept();
        } while(answerConcept == null || !this.hasConceptMapping(answerConcept, "org.openmrs.module.emrapi", codeForAnswer));

        return answerConcept;
    }

    private boolean hasConceptMapping(Concept concept, String sourceName, String codeToLookFor) {
        Iterator i$ = concept.getConceptMappings().iterator();

        ConceptReferenceTerm conceptReferenceTerm;
        do {
            if(!i$.hasNext()) {
                return false;
            }

            ConceptMap conceptMap = (ConceptMap)i$.next();
            conceptReferenceTerm = conceptMap.getConceptReferenceTerm();
        } while(!sourceName.equals(conceptReferenceTerm.getConceptSource().getName()) || !codeToLookFor.equals(conceptReferenceTerm.getCode()));

        return true;
    }
}
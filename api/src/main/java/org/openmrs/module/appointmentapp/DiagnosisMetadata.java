package org.openmrs.module.appointmentapp;

import org.openmrs.*;
import org.openmrs.api.ConceptService;

import java.util.Iterator;

/**
 * @author Stanslaus Odhaimbo
 *         Created on 23/03/2017.
 */
public class DiagnosisMetadata extends ConceptSetDescriptor {
    private Concept diagnosisSetConcept;
    private Concept codedDiagnosisConcept;
    private Concept nonCodedDiagnosisConcept;
    private Concept diagnosisOrderConcept;
    private Concept diagnosisCertaintyConcept;
    private ConceptSource emrConceptSource;

    public DiagnosisMetadata(ConceptService conceptService, ConceptSource emrConceptSource) {
        this.setup(conceptService, "org.openmrs.module.emrapi", ConceptSetDescriptorField.required("diagnosisSetConcept", "Diagnosis Concept Set"), new ConceptSetDescriptorField[]{ConceptSetDescriptorField.required("codedDiagnosisConcept", "Coded Diagnosis"), ConceptSetDescriptorField.required("nonCodedDiagnosisConcept", "Non-Coded Diagnosis"), ConceptSetDescriptorField.required("diagnosisOrderConcept", "Diagnosis Order"), ConceptSetDescriptorField.required("diagnosisCertaintyConcept", "Diagnosis Certainty")});
        this.emrConceptSource = emrConceptSource;
    }

    public DiagnosisMetadata() {
    }

    public Concept getDiagnosisSetConcept() {
        return this.diagnosisSetConcept;
    }

    public Concept getCodedDiagnosisConcept() {
        return this.codedDiagnosisConcept;
    }

    public Concept getNonCodedDiagnosisConcept() {
        return this.nonCodedDiagnosisConcept;
    }

    public Concept getDiagnosisOrderConcept() {
        return this.diagnosisOrderConcept;
    }

    public Concept getDiagnosisCertaintyConcept() {
        return this.diagnosisCertaintyConcept;
    }

    public void setDiagnosisSetConcept(Concept diagnosisSetConcept) {
        this.diagnosisSetConcept = diagnosisSetConcept;
    }

    public void setCodedDiagnosisConcept(Concept codedDiagnosisConcept) {
        this.codedDiagnosisConcept = codedDiagnosisConcept;
    }

    public void setNonCodedDiagnosisConcept(Concept nonCodedDiagnosisConcept) {
        this.nonCodedDiagnosisConcept = nonCodedDiagnosisConcept;
    }

    public void setDiagnosisOrderConcept(Concept diagnosisOrderConcept) {
        this.diagnosisOrderConcept = diagnosisOrderConcept;
    }

    public void setDiagnosisCertaintyConcept(Concept diagnosisCertaintyConcept) {
        this.diagnosisCertaintyConcept = diagnosisCertaintyConcept;
    }

    public void setEmrConceptSource(ConceptSource emrConceptSource) {
        this.emrConceptSource = emrConceptSource;
    }

    public Obs buildDiagnosisObsGroup(Diagnosis diagnosis) {
        Concept orderAnswer = this.findAnswer(this.diagnosisOrderConcept, diagnosis.getOrder().getCodeInEmrConceptSource());
        Concept certaintyAnswer = this.findAnswer(this.diagnosisCertaintyConcept, diagnosis.getCertainty().getCodeInEmrConceptSource());
        if(diagnosis.getExistingObs() != null) {
            this.setCodedMember(diagnosis.getExistingObs(), this.diagnosisOrderConcept, orderAnswer, (ConceptName)null);
            this.setCodedMember(diagnosis.getExistingObs(), this.diagnosisCertaintyConcept, certaintyAnswer, (ConceptName)null);
            this.setCodedOrFreeTextMember(diagnosis.getExistingObs(), diagnosis.getDiagnosis(), this.codedDiagnosisConcept, this.nonCodedDiagnosisConcept);
            return diagnosis.getExistingObs();
        } else {
            Obs order = this.buildObsFor(this.diagnosisOrderConcept, orderAnswer, (ConceptName)null);
            Obs certainty = this.buildObsFor(this.diagnosisCertaintyConcept, certaintyAnswer, (ConceptName)null);
            Obs diagnosisObs = this.buildObsFor(diagnosis.getDiagnosis(), this.codedDiagnosisConcept, this.nonCodedDiagnosisConcept);
            Obs obs = new Obs();
            obs.setConcept(this.diagnosisSetConcept);
            obs.addGroupMember(order);
            obs.addGroupMember(certainty);
            obs.addGroupMember(diagnosisObs);
            return obs;
        }
    }

    public boolean isDiagnosis(Obs obsGroup) {
        return obsGroup.getConcept().equals(this.diagnosisSetConcept);
    }

    public boolean isPrimaryDiagnosis(Obs obsGroup) {
        return this.isDiagnosis(obsGroup) && this.hasDiagnosisOrder(obsGroup, "Primary");
    }

    private boolean hasDiagnosisOrder(Obs obsGroup, String codeForDiagnosisOrderToCheckFor) {
        this.findMember(obsGroup, this.diagnosisOrderConcept);
        throw new RuntimeException("Not Yet Implemented");
    }


    private String findMapping(Concept concept) {
        Iterator i$ = concept.getConceptMappings().iterator();

        ConceptReferenceTerm conceptReferenceTerm;
        do {
            if(!i$.hasNext()) {
                return null;
            }

            ConceptMap conceptMap = (ConceptMap)i$.next();
            conceptReferenceTerm = conceptMap.getConceptReferenceTerm();
        } while(!conceptReferenceTerm.getConceptSource().equals(this.emrConceptSource));

        return conceptReferenceTerm.getCode();
    }

    private CodedOrFreeTextAnswer buildFrom(Obs codedObs, Obs nonCodedObs) {
        return codedObs != null?(codedObs.getValueCodedName() != null?new CodedOrFreeTextAnswer(codedObs.getValueCodedName()):new CodedOrFreeTextAnswer(codedObs.getValueCoded())):new CodedOrFreeTextAnswer(nonCodedObs.getValueText());
    }
}

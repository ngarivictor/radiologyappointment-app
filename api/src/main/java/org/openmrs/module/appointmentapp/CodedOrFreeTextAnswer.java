package org.openmrs.module.appointmentapp;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.util.OpenmrsUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author Stanslaus Odhaimbo
 *         Created on 23/03/2017.
 */
public class CodedOrFreeTextAnswer {
    public static final String CONCEPT_NAME_PREFIX = "ConceptName:";
    public static final String CONCEPT_UUID_PREFIX = "ConceptUuid:";
    public static final String CONCEPT_PREFIX = "Concept:";
    public static final String NON_CODED_PREFIX = "Non-Coded:";
    Concept codedAnswer;
    ConceptName specificCodedAnswer;
    String nonCodedAnswer;

    public CodedOrFreeTextAnswer() {
    }

    public CodedOrFreeTextAnswer(String spec, ConceptService conceptService) {
        String conceptUuid;
        if(spec.startsWith("ConceptName:")) {
            conceptUuid = spec.substring("ConceptName:".length());
            this.setSpecificCodedAnswer(conceptService.getConceptName(Integer.valueOf(conceptUuid)));
        } else if(spec.startsWith("Concept:")) {
            conceptUuid = spec.substring("Concept:".length());
            this.setCodedAnswer(conceptService.getConcept(Integer.valueOf(conceptUuid)));
        } else if(spec.startsWith("ConceptUuid:")) {
            conceptUuid = spec.substring("ConceptUuid:".length());
            this.setCodedAnswer(conceptService.getConceptByUuid(conceptUuid));
        } else {
            if(!spec.startsWith("Non-Coded:")) {
                throw new IllegalArgumentException("Unknown format: " + spec);
            }

            this.setNonCodedAnswer(spec.substring("Non-Coded:".length()));
        }

    }

    public CodedOrFreeTextAnswer(Obs codedOrNonCodedValue) {
        if(codedOrNonCodedValue.getValueCodedName() != null) {
            this.specificCodedAnswer = codedOrNonCodedValue.getValueCodedName();
            this.codedAnswer = this.specificCodedAnswer.getConcept();
        } else if(codedOrNonCodedValue.getValueCoded() != null) {
            this.codedAnswer = codedOrNonCodedValue.getValueCoded();
        } else {
            if(codedOrNonCodedValue.getValueText() == null) {
                throw new IllegalArgumentException("codedOrNonCodedValue must have one of valueCodedName, valueCoded, or valueText");
            }

            this.nonCodedAnswer = codedOrNonCodedValue.getValueText();
        }

    }

    public CodedOrFreeTextAnswer(Concept codedAnswer) {
        this.codedAnswer = codedAnswer;
    }

    public CodedOrFreeTextAnswer(ConceptName specificCodedAnswer) {
        this.specificCodedAnswer = specificCodedAnswer;
        this.codedAnswer = specificCodedAnswer.getConcept();
    }

    public CodedOrFreeTextAnswer(String nonCodedAnswer) {
        this.nonCodedAnswer = nonCodedAnswer;
    }

    public String toClientString() {
        return this.specificCodedAnswer != null?"ConceptName:" + this.specificCodedAnswer.getId():(this.codedAnswer != null?"Concept:" + this.codedAnswer.getId():"Non-Coded:" + this.nonCodedAnswer);
    }

    public Object getValue() {
        return this.specificCodedAnswer != null?this.specificCodedAnswer:(this.codedAnswer != null?this.codedAnswer:this.nonCodedAnswer);
    }

    public Concept getCodedAnswer() {
        return this.codedAnswer;
    }

    @JsonDeserialize(
            using = ConceptCodeDeserializer.class
    )
    public void setCodedAnswer(Concept codedAnswer) {
        this.codedAnswer = codedAnswer;
    }

    public ConceptName getSpecificCodedAnswer() {
        return this.specificCodedAnswer;
    }

    public void setSpecificCodedAnswer(ConceptName specificCodedAnswer) {
        this.specificCodedAnswer = specificCodedAnswer;
        this.codedAnswer = specificCodedAnswer.getConcept();
    }

    public String getNonCodedAnswer() {
        return this.nonCodedAnswer;
    }

    public void setNonCodedAnswer(String nonCodedAnswer) {
        this.nonCodedAnswer = nonCodedAnswer;
    }

    public boolean equals(Object o) {
        if(o != null && o instanceof CodedOrFreeTextAnswer) {
            CodedOrFreeTextAnswer other = (CodedOrFreeTextAnswer)o;
            return OpenmrsUtil.nullSafeEquals(this.codedAnswer, other.codedAnswer) && OpenmrsUtil.nullSafeEquals(this.specificCodedAnswer, other.specificCodedAnswer) && OpenmrsUtil.nullSafeEquals(this.nonCodedAnswer, other.nonCodedAnswer);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (new HashCodeBuilder(17, 37)).append(this.codedAnswer).append(this.specificCodedAnswer).append(this.nonCodedAnswer).toHashCode();
    }

    public String formatWithoutSpecificAnswer(Locale locale) {
        return this.nonCodedAnswer != null?this.nonCodedAnswer:(this.codedAnswer == null?"?":this.codedAnswer.getName(locale).getName());
    }

    public String format(Locale locale) {
        if(this.nonCodedAnswer != null) {
            return "\"" + this.nonCodedAnswer + "\"";
        } else if(this.codedAnswer == null) {
            return "?";
        } else if(this.specificCodedAnswer == null) {
            return this.codedAnswer.getName(locale).getName();
        } else if(this.specificCodedAnswer.isLocalePreferred().booleanValue() && this.specificCodedAnswer.getLocale().equals(locale)) {
            return this.specificCodedAnswer.getName();
        } else {
            ConceptName preferredName = this.codedAnswer.getName(locale);
            return preferredName != null && !preferredName.getName().equals(this.specificCodedAnswer.getName())?this.specificCodedAnswer.getName() + " → " + preferredName.getName():this.specificCodedAnswer.getName();
        }
    }

    public String formatWithCode(Locale locale, List<ConceptSource> codeFromSources) {
        if(this.codedAnswer == null) {
            return this.format(locale);
        } else {
            String formatted = this.format(locale);
            ConceptReferenceTerm mappedTerm = this.getBestMapping(this.codedAnswer, codeFromSources);
            return mappedTerm == null?formatted:formatted + " [" + mappedTerm.getCode() + "]";
        }
    }

    private ConceptReferenceTerm getBestMapping(Concept concept, List<ConceptSource> fromSources) {
        ConceptReferenceTerm nextBest = null;
        Iterator i$ = concept.getConceptMappings().iterator();

        while(i$.hasNext()) {
            ConceptMap candidate = (ConceptMap)i$.next();
            if(fromSources.contains(candidate.getConceptReferenceTerm().getConceptSource())) {
                if(candidate.getConceptMapType().getUuid().equals("35543629-7d8c-11e1-909d-c80aa9edcf4e")) {
                    return candidate.getConceptReferenceTerm();
                }

                if(candidate.getConceptMapType().getUuid().equals("43ac5109-7d8c-11e1-909d-c80aa9edcf4e")) {
                    nextBest = candidate.getConceptReferenceTerm();
                }
            }
        }

        return nextBest;
    }
}

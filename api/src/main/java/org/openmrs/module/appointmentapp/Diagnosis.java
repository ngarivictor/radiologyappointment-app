package org.openmrs.module.appointmentapp;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openmrs.Obs;
import org.openmrs.util.OpenmrsUtil;

/**
 * @author Stanslaus Odhaimbo
 *         Created on 23/03/2017.
 */
public class Diagnosis {
    CodedOrFreeTextAnswer diagnosis;
    @JsonProperty
    Diagnosis.Order order;
    @JsonProperty
    Diagnosis.Certainty certainty;
    Obs existingObs;

    public Diagnosis() {
        this.certainty = Diagnosis.Certainty.PRESUMED;
    }

    public Diagnosis(CodedOrFreeTextAnswer diagnosis) {
        this.certainty = Diagnosis.Certainty.PRESUMED;
        this.diagnosis = diagnosis;
    }

    public Diagnosis(CodedOrFreeTextAnswer diagnosis, Diagnosis.Order order) {
        this.certainty = Diagnosis.Certainty.PRESUMED;
        this.diagnosis = diagnosis;
        this.order = order;
    }

    public CodedOrFreeTextAnswer getDiagnosis() {
        return this.diagnosis;
    }

    public void setDiagnosis(CodedOrFreeTextAnswer diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Diagnosis.Order getOrder() {
        return this.order;
    }

    public void setOrder(Diagnosis.Order order) {
        this.order = order;
    }

    public Diagnosis.Certainty getCertainty() {
        return this.certainty;
    }

    public void setCertainty(Diagnosis.Certainty certainty) {
        this.certainty = certainty;
    }

    public Obs getExistingObs() {
        return this.existingObs;
    }

    public void setExistingObs(Obs existingObs) {
        this.existingObs = existingObs;
    }

    public boolean equals(Object o) {
        if (o != null && o instanceof Diagnosis) {
            Diagnosis other = (Diagnosis) o;
            return OpenmrsUtil.nullSafeEquals(this.diagnosis, other.getDiagnosis()) && OpenmrsUtil.nullSafeEquals(this.order, other.getOrder());
        } else {
            return false;
        }
    }

    public static enum Certainty {
        CONFIRMED("Confirmed"),
        PRESUMED("Presumed");

        String codeInEmrConceptSource;

        private Certainty(String codeInEmrConceptSource) {
            this.codeInEmrConceptSource = codeInEmrConceptSource;
        }

        String getCodeInEmrConceptSource() {
            return this.codeInEmrConceptSource;
        }

        public static Diagnosis.Certainty parseConceptReferenceCode(String code) {
            Diagnosis.Certainty[] arr$ = values();
            int len$ = arr$.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                Diagnosis.Certainty candidate = arr$[i$];
                if (candidate.getCodeInEmrConceptSource().equals(code)) {
                    return candidate;
                }
            }

            return null;
        }
    }

    public static enum Order {
        PRIMARY("Primary"),
        SECONDARY("Secondary");

        String codeInEmrConceptSource;

        private Order(String codeInEmrConceptSource) {
            this.codeInEmrConceptSource = codeInEmrConceptSource;
        }

        String getCodeInEmrConceptSource() {
            return this.codeInEmrConceptSource;
        }

        public static Diagnosis.Order parseConceptReferenceCode(String code) {
            Diagnosis.Order[] arr$ = values();
            int len$ = arr$.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                Diagnosis.Order candidate = arr$[i$];
                if (candidate.getCodeInEmrConceptSource().equals(code)) {
                    return candidate;
                }
            }

            return null;
        }
    }
}

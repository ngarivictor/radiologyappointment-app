package org.openmrs.module.appointmentapp;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 * Created by mstan on 23/03/2017.
 */
public class DispositionObs {
    @JsonProperty
    private String label;
    @JsonProperty
    private String conceptCode;
    @JsonProperty
    private Map<String, String> params;

    public DispositionObs() {
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            DispositionObs that = (DispositionObs)o;
            if(!this.getLabel().equals(that.getLabel())) {
                return false;
            } else if(!this.getConceptCode().equals(that.conceptCode)) {
                return false;
            } else {
                if(this.params != null) {
                    if(!this.params.equals(that.params)) {
                        return false;
                    }
                } else if(that.params != null) {
                    return false;
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getConceptCode() {
        return this.conceptCode;
    }

    public void setConceptCode(String conceptCode) {
        this.conceptCode = conceptCode;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}

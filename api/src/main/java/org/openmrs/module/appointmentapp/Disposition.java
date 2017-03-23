package org.openmrs.module.appointmentapp;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by mstan on 23/03/2017.
 */
public class Disposition {
    @JsonProperty
    private String uuid;
    @JsonProperty
    private String name;
    @JsonProperty
    private String conceptCode;
    @JsonProperty
    private DispositionType type;
    @JsonProperty
    private List<CareSettingType> careSettingTypes;
    @JsonProperty
    private Boolean keepsVisitOpen;
    @JsonProperty
    private List<String> actions;
    @JsonProperty
    private List<DispositionObs> additionalObs;

    public Disposition() {
    }

    public Disposition(String uuid, String name, String conceptCode, List<String> actions, List<DispositionObs> additionalObs) {
        this.uuid = uuid;
        this.name = name;
        this.conceptCode = conceptCode;
        this.actions = actions;
        this.additionalObs = additionalObs;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            Disposition that;
            label45: {
                that = (Disposition)o;
                if(this.actions != null) {
                    if(this.actions.equals(that.actions)) {
                        break label45;
                    }
                } else if(that.actions == null) {
                    break label45;
                }

                return false;
            }

            if(this.additionalObs != null) {
                if(!this.additionalObs.equals(that.additionalObs)) {
                    return false;
                }
            } else if(that.additionalObs != null) {
                return false;
            }

            if(!this.conceptCode.equals(that.conceptCode)) {
                return false;
            } else if(!this.name.equals(that.name)) {
                return false;
            } else if(!this.uuid.equals(that.uuid)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public List<String> getActions() {
        return this.actions;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public String getConceptCode() {
        return this.conceptCode;
    }

    public void setConceptCode(String conceptCode) {
        this.conceptCode = conceptCode;
    }

    public DispositionType getType() {
        return this.type;
    }

    public void setType(DispositionType type) {
        this.type = type;
    }

    public List<CareSettingType> getCareSettingTypes() {
        return this.careSettingTypes;
    }

    public void setCareSettingTypes(List<CareSettingType> careSettingTypes) {
        this.careSettingTypes = careSettingTypes;
    }

    public Boolean getKeepsVisitOpen() {
        return this.keepsVisitOpen;
    }

    public void setKeepsVisitOpen(Boolean keepsVisitOpen) {
        this.keepsVisitOpen = keepsVisitOpen;
    }

    public List<DispositionObs> getAdditionalObs() {
        return this.additionalObs;
    }

    public void setAdditionalObs(List<DispositionObs> additionalObs) {
        this.additionalObs = additionalObs;
    }

    public int hashCode() {
        int result = this.uuid.hashCode();
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.conceptCode.hashCode();
        result = 31 * result + (this.actions != null?this.actions.hashCode():0);
        result = 31 * result + (this.additionalObs != null?this.additionalObs.hashCode():0);
        return result;
    }
}

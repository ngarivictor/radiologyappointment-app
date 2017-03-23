package org.openmrs.module.appointmentapp;

import org.openmrs.Location;
import org.openmrs.PatientIdentifierType;

/**
 * Created by mstan on 23/03/2017.
 */
public class AutoGenerationOption {
    private Integer id;
    private PatientIdentifierType identifierType;
    private Location location;
    private IdentifierSource source;
    private boolean manualEntryEnabled;
    private boolean automaticGenerationEnabled;

    public AutoGenerationOption() {
        this.manualEntryEnabled = true;
        this.automaticGenerationEnabled = false;
    }

    public AutoGenerationOption(PatientIdentifierType identifierType) {
        this();
        this.identifierType = identifierType;
    }

    public AutoGenerationOption(PatientIdentifierType identifierType, IdentifierSource source, boolean manualEntryEnabled, boolean automaticGenerationEnabled) {
        this(identifierType);
        this.source = source;
        this.manualEntryEnabled = manualEntryEnabled;
        this.automaticGenerationEnabled = automaticGenerationEnabled;
    }

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof AutoGenerationOption) {
            AutoGenerationOption that = (AutoGenerationOption)obj;
            if(this.getId() != null) {
                return this.getId().equals(that.getId());
            }
        }

        return this == obj;
    }

    public int hashCode() {
        return this.getId() != null?31 * this.getId().hashCode():super.hashCode();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PatientIdentifierType getIdentifierType() {
        return this.identifierType;
    }

    public void setIdentifierType(PatientIdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public IdentifierSource getSource() {
        return this.source;
    }

    public void setSource(IdentifierSource source) {
        this.source = source;
    }

    public boolean isManualEntryEnabled() {
        return this.manualEntryEnabled;
    }

    public void setManualEntryEnabled(boolean manualEntryEnabled) {
        this.manualEntryEnabled = manualEntryEnabled;
    }

    public boolean isAutomaticGenerationEnabled() {
        return this.automaticGenerationEnabled;
    }

    public void setAutomaticGenerationEnabled(boolean automaticGenerationEnabled) {
        this.automaticGenerationEnabled = automaticGenerationEnabled;
    }
}

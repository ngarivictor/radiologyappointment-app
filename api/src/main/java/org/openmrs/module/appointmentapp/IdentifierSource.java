package org.openmrs.module.appointmentapp;

import org.openmrs.PatientIdentifierType;
import org.openmrs.User;

import java.util.Date;
import java.util.Set;

/**
 * Created by mstan on 23/03/2017.
 */
public abstract class IdentifierSource {
    public IdentifierSource() {
    }

    public abstract Integer getId();

    public abstract void setId(Integer var1);

    public abstract String getUuid();

    public abstract void setUuid(String var1);

    public abstract String getName();

    public abstract void setName(String var1);

    public abstract String getDescription();

    public abstract void setDescription(String var1);

    public abstract PatientIdentifierType getIdentifierType();

    public abstract void setIdentifierType(PatientIdentifierType var1);

    public abstract User getCreator();

    public abstract void setCreator(User var1);

    public abstract Date getDateCreated();

    public abstract void setDateCreated(Date var1);

    public abstract User getChangedBy();

    public abstract void setChangedBy(User var1);

    public abstract Date getDateChanged();

    public abstract void setDateChanged(Date var1);

    public abstract Boolean isRetired();

    public abstract void setRetired(Boolean var1);

    public abstract User getRetiredBy();

    public abstract void setRetiredBy(User var1);

    public abstract Date getDateRetired();

    public abstract void setDateRetired(Date var1);

    public abstract String getRetireReason();

    public abstract void setRetireReason(String var1);

    public abstract Set<String> getReservedIdentifiers();

    public abstract void setReservedIdentifiers(Set<String> var1);

    public abstract void addReservedIdentifier(String var1);
}

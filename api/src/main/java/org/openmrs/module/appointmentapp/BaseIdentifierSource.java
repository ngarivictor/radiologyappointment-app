package org.openmrs.module.appointmentapp;

import org.openmrs.PatientIdentifierType;
import org.openmrs.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mstan on 23/03/2017.
 */
public abstract class BaseIdentifierSource extends IdentifierSource {
    private Integer id;
    private String uuid;
    private String name;
    private String description;
    private PatientIdentifierType identifierType;
    private User creator;
    private Date dateCreated;
    private User changedBy;
    private Date dateChanged;
    private Boolean retired;
    private User retiredBy;
    private Date dateRetired;
    private String retireReason;
    private Set<String> reservedIdentifiers;

    public BaseIdentifierSource() {
        this.retired = Boolean.FALSE;
    }

    public void addReservedIdentifier(String reservedIdentifier) {
        this.getReservedIdentifiers().add(reservedIdentifier);
    }

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof IdentifierSource) {
            IdentifierSource that = (IdentifierSource)obj;
            if(this.getId() != null) {
                return this.getId().equals(that.getId());
            }

            if(this.getUuid() != null) {
                return this.getUuid().equals(that.getUuid());
            }
        }

        return this == obj;
    }

    public int hashCode() {
        int result = this.id != null?this.id.hashCode():0;
        result = 31 * result + (this.uuid != null?this.uuid.hashCode():0);
        return result;
    }

    public String toString() {
        return this.getName();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PatientIdentifierType getIdentifierType() {
        return this.identifierType;
    }

    public void setIdentifierType(PatientIdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getChangedBy() {
        return this.changedBy;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public Date getDateChanged() {
        return this.dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Boolean getRetired() {
        return this.retired;
    }

    public Boolean isRetired() {
        return this.retired;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public User getRetiredBy() {
        return this.retiredBy;
    }

    public void setRetiredBy(User retiredBy) {
        this.retiredBy = retiredBy;
    }

    public Date getDateRetired() {
        return this.dateRetired;
    }

    public void setDateRetired(Date dateRetired) {
        this.dateRetired = dateRetired;
    }

    public String getRetireReason() {
        return this.retireReason;
    }

    public void setRetireReason(String retireReason) {
        this.retireReason = retireReason;
    }

    public Set<String> getReservedIdentifiers() {
        if(this.reservedIdentifiers == null) {
            this.reservedIdentifiers = new HashSet();
        }

        return this.reservedIdentifiers;
    }

    public void setReservedIdentifiers(Set<String> reservedIdentifiers) {
        this.reservedIdentifiers = reservedIdentifiers;
    }
}
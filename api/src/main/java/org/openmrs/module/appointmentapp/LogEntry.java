package org.openmrs.module.appointmentapp;

import org.openmrs.User;

import java.util.Date;

/**
 * Created by mstan on 23/03/2017.
 */
public class LogEntry {
    private Integer id;
    private IdentifierSource source;
    private String identifier;
    private Date dateGenerated;
    private User generatedBy;
    private String comment;

    public LogEntry() {
    }

    public LogEntry(IdentifierSource source, String identifier, Date dateGenerated, User generatedBy, String comment) {
        this.source = source;
        this.identifier = identifier;
        this.dateGenerated = dateGenerated;
        this.generatedBy = generatedBy;
        this.comment = comment;
    }

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof LogEntry) {
            LogEntry that = (LogEntry)obj;
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

    public IdentifierSource getSource() {
        return this.source;
    }

    public void setSource(IdentifierSource source) {
        this.source = source;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getDateGenerated() {
        return this.dateGenerated;
    }

    public void setDateGenerated(Date dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public User getGeneratedBy() {
        return this.generatedBy;
    }

    public void setGeneratedBy(User generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

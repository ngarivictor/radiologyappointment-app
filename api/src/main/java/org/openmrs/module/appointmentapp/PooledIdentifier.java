package org.openmrs.module.appointmentapp;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mstan on 23/03/2017.
 */
public class PooledIdentifier {
    private Integer id;
    private String uuid;
    private IdentifierPool pool;
    private String identifier;
    private Date dateUsed;
    private String comment;

    public PooledIdentifier() {
        this.uuid = UUID.randomUUID().toString();
    }

    public PooledIdentifier(IdentifierPool pool, String identifier) {
        this(pool, identifier, (Date)null, (String)null);
    }

    public PooledIdentifier(IdentifierPool pool, String identifier, Date dateUsed, String comment) {
        this();
        this.pool = pool;
        this.identifier = identifier;
        this.dateUsed = dateUsed;
        this.comment = comment;
    }

    public boolean isAvailable() {
        return this.dateUsed == null;
    }

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof PooledIdentifier) {
            PooledIdentifier that = (PooledIdentifier)obj;
            if(this.getId() != null) {
                return this.getId().equals(that.getId());
            }
        }

        return this == obj;
    }

    public int hashCode() {
        return this.getId() != null?31 * this.getId().hashCode():super.hashCode();
    }

    public String toString() {
        return this.getPool().getName() + ": " + this.getIdentifier();
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

    public IdentifierPool getPool() {
        return this.pool;
    }

    public void setPool(IdentifierPool pool) {
        this.pool = pool;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getDateUsed() {
        return this.dateUsed;
    }

    public void setDateUsed(Date dateUsed) {
        this.dateUsed = dateUsed;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

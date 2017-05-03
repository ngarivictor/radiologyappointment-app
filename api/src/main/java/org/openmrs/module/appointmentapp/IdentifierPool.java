package org.openmrs.module.appointmentapp;

import java.util.*;

/**
 * Created by mstan on 23/03/2017.
 */
public class IdentifierPool extends BaseIdentifierSource {
    private IdentifierSource source;
    private int batchSize = 1000;
    private int minPoolSize = 500;
    private boolean sequential = false;
    private Set<PooledIdentifier> identifiers;
    private boolean refillWithScheduledTask = true;

    public IdentifierPool() {
    }

    public Set<PooledIdentifier> getAvailableIdentifiers() {
        HashSet ret = new HashSet();
        Iterator i$ = this.getIdentifiers().iterator();

        while(i$.hasNext()) {
            PooledIdentifier i = (PooledIdentifier)i$.next();
            if(i.isAvailable()) {
                ret.add(i);
            }
        }

        return Collections.unmodifiableSet(ret);
    }

    public Set<PooledIdentifier> getUsedIdentifiers() {
        HashSet ret = new HashSet();
        Iterator i$ = this.getIdentifiers().iterator();

        while(i$.hasNext()) {
            PooledIdentifier i = (PooledIdentifier)i$.next();
            if(!i.isAvailable()) {
                ret.add(i);
            }
        }

        return Collections.unmodifiableSet(ret);
    }

    public synchronized String nextIdentifier() {
        Iterator i$ = this.getIdentifiers().iterator();

        PooledIdentifier p;
        do {
            if(!i$.hasNext()) {
                throw new RuntimeException("Not enough available identifiers in pool");
            }

            p = (PooledIdentifier)i$.next();
        } while(!p.isAvailable());

        p.setDateUsed(new Date());
        return p.getIdentifier();
    }

    public synchronized void addIdentifierToPool(String identifier) {
        this.getIdentifiers().add(new PooledIdentifier(this, identifier));
    }

    public IdentifierSource getSource() {
        return this.source;
    }

    public void setSource(IdentifierSource source) {
        this.source = source;
    }

    public int getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getMinPoolSize() {
        return this.minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public boolean isSequential() {
        return this.sequential;
    }

    public void setSequential(boolean sequential) {
        this.sequential = sequential;
    }

    public Set<PooledIdentifier> getIdentifiers() {
        if(this.identifiers == null) {
            this.identifiers = new LinkedHashSet();
        }

        return this.identifiers;
    }

    public void setIdentifiers(Set<PooledIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    public boolean isRefillWithScheduledTask() {
        return this.refillWithScheduledTask;
    }

    public void setRefillWithScheduledTask(boolean refillWithScheduledTask) {
        this.refillWithScheduledTask = refillWithScheduledTask;
    }
}
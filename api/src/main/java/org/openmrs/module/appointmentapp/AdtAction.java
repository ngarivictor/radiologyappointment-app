package org.openmrs.module.appointmentapp;

import org.openmrs.*;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by mstan on 23/03/2017.
 */
public class AdtAction {
    private Visit visit;
    private Location location;
    private Date actionDatetime;
    private Map<EncounterRole, Set<Provider>> providers;

    public AdtAction(Visit visit, Location toLocation, Map<EncounterRole, Set<Provider>> providers) {
        this.visit = visit;
        this.location = toLocation;
        this.providers = providers;
    }

    public Visit getVisit() {
        return this.visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getActionDatetime() {
        return this.actionDatetime;
    }

    public void setActionDatetime(Date actionDatetime) {
        this.actionDatetime = actionDatetime;
    }

    public Map<EncounterRole, Set<Provider>> getProviders() {
        return this.providers;
    }

    public void setProviders(Map<EncounterRole, Set<Provider>> providers) {
        this.providers = providers;
    }


}

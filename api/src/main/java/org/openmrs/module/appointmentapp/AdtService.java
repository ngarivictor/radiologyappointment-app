package org.openmrs.module.appointmentapp;

import org.openmrs.*;
import org.openmrs.api.OpenmrsService;

import java.util.Date;
import java.util.List;

/**
 * Created by mstan on 23/03/2017.
 */
public interface AdtService extends OpenmrsService {

    void closeAndSaveVisit(Visit var1);

    Visit ensureActiveVisit(Patient var1, Location var2);

    Visit ensureVisit(Patient var1, Date var2, Location var3);

    Encounter checkInPatient(Patient var1, Location var2, Provider var3, List<Obs> var4, List<Order> var5, boolean var6);

    Location getLocationThatSupportsVisits(Location var1);

    List<Location> getAllLocationsThatSupportVisits();

    boolean isSuitableVisit(Visit var1, Location var2, Date var3);

    void closeInactiveVisits();

    Encounter getLastEncounter(Patient var1);

    int getCountOfEncounters(Patient var1);

    int getCountOfVisits(Patient var1);

    boolean visitsOverlap(Visit var1, Visit var2);

    void mergePatients(Patient var1, Patient var2);



    Visit mergeConsecutiveVisits(List<Integer> var1, Patient var2);

    Visit mergeVisits(Visit var1, Visit var2);

    boolean areConsecutiveVisits(List<Integer> var1, Patient var2);

    boolean hasVisitDuring(Patient var1, Location var2, Date var3, Date var4);

    List<Location> getInpatientLocations();
}

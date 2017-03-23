package org.openmrs.module.appointmentapp;

import org.openmrs.*;
import org.openmrs.api.*;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.util.OpenmrsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by mstan on 23/03/2017.
 */
@Component("adtService")
public class AdtServiceImpl extends BaseOpenmrsService implements AdtService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private EmrApiProperties emrApiProperties;
    private PatientService patientService;
    private EncounterService encounterService;
    private VisitService visitService;
    private ProviderService providerService;
    private LocationService locationService;

    public AdtServiceImpl() {
    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public void setEmrApiProperties(EmrApiProperties emrApiProperties) {
        this.emrApiProperties = emrApiProperties;
    }

    public void setEncounterService(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    public void setProviderService(ProviderService providerService) {
        this.providerService = providerService;
    }


    public void closeInactiveVisits() {
        List openVisits = this.visitService.getVisits((Collection) null, (Collection) null, (Collection) null, (Collection) null, (Date) null, (Date) null, (Date) null, (Date) null, (Map) null, false, false);
        Iterator i$ = openVisits.iterator();

        while (i$.hasNext()) {
            Visit visit = (Visit) i$.next();

            try {
                this.closeAndSaveVisit(visit);
            } catch (Exception var5) {
                this.log.warn("Failed to close inactive visit " + visit, var5);
            }
        }


    }


    public boolean visitsOverlap(Visit v1, Visit v2) {
        Location where1 = v1.getLocation();
        Location where2 = v2.getLocation();
        return (where1 != null || where2 != null) && !this.isSameOrAncestor(where1, where2) && !this.isSameOrAncestor(where2, where1) ? false : OpenmrsUtil.compareWithNullAsLatest(v1.getStartDatetime(), v2.getStopDatetime()) <= 0 && OpenmrsUtil.compareWithNullAsLatest(v2.getStartDatetime(), v1.getStopDatetime()) <= 0;
    }

    private Visit getActiveVisitHelper(Patient patient, Location department) {
        Date now = new Date();
        List candidates = this.visitService.getVisitsByPatient(patient);
        Visit ret = null;
        Iterator i$ = candidates.iterator();

        while (i$.hasNext()) {
            Visit candidate = (Visit) i$.next();
            if (this.isSuitableVisit(candidate, department, now)) {
                ret = candidate;
            }
        }

        return ret;
    }


    @Transactional
    public void closeAndSaveVisit(Visit visit) {
        visit.setStopDatetime(this.guessVisitStopDatetime(visit));
        this.visitService.saveVisit(visit);
    }

    @Transactional
    public Visit ensureActiveVisit(Patient patient, Location department) {
        Visit activeVisit = this.getActiveVisitHelper(patient, department);
        if (activeVisit == null) {
            Date now = new Date();
            activeVisit = this.buildVisit(patient, department, now);
            this.visitService.saveVisit(activeVisit);
        }

        return activeVisit;
    }

    @Transactional
    public Visit ensureVisit(Patient patient, Date visitTime, Location department) {
        if (visitTime == null) {
            visitTime = new Date();
        }

        Visit visit = null;
        List patientList = Collections.singletonList(patient);
        List candidates = this.visitService.getVisits((Collection) null, patientList, (Collection) null, (Collection) null, (Date) null, visitTime, (Date) null, (Date) null, (Map) null, true, false);
        if (candidates != null) {
            Iterator i$ = candidates.iterator();

            while (i$.hasNext()) {
                Visit candidate = (Visit) i$.next();
                if (this.isSuitableVisit(candidate, department, visitTime)) {
                    return candidate;
                }
            }
        }

        if (visit == null) {
            visit = this.buildVisit(patient, department, visitTime);
            this.visitService.saveVisit(visit);
        }

        return visit;
    }

    private Date guessVisitStopDatetime(Visit visit) {
        if (visit.getEncounters() != null && visit.getEncounters().size() != 0) {
            Iterator iterator = visit.getEncounters().iterator();
            Encounter latest = (Encounter) iterator.next();

            while (iterator.hasNext()) {
                Encounter candidate = (Encounter) iterator.next();
                if (OpenmrsUtil.compare(candidate.getEncounterDatetime(), latest.getEncounterDatetime()) > 0) {
                    latest = candidate;
                }
            }

            return latest.getEncounterDatetime();
        } else {
            return visit.getStartDatetime();
        }
    }

    @Transactional
    public synchronized Encounter checkInPatient(Patient patient, Location where, Provider checkInClerk, List<Obs> obsForCheckInEncounter, List<Order> ordersForCheckInEncounter, boolean newVisit) {
        if (checkInClerk == null) {
            checkInClerk = this.getProvider(Context.getAuthenticatedUser());
        }

        Visit activeVisit = this.getActiveVisitHelper(patient, where);
        if (activeVisit != null && newVisit) {
            this.closeAndSaveVisit(activeVisit);
            activeVisit = null;
        }

        if (activeVisit == null) {
            activeVisit = this.ensureActiveVisit(patient, where);
        }

        Encounter lastEncounter = this.getLastEncounter(patient);
        if (lastEncounter != null && activeVisit.equals(lastEncounter.getVisit()) && this.emrApiProperties.getCheckInEncounterType().equals(lastEncounter.getEncounterType()) && where.equals(lastEncounter.getLocation())) {
            this.log.warn("Patient id:{} tried to check-in twice in a row at id:{} during the same visit", patient.getId(), where.getId());
            return lastEncounter;
        } else {
            Encounter encounter = this.buildEncounter(this.emrApiProperties.getCheckInEncounterType(), patient, where, (Form) null, new Date(), obsForCheckInEncounter, ordersForCheckInEncounter);
            encounter.addProvider(this.emrApiProperties.getCheckInClerkEncounterRole(), checkInClerk);
            activeVisit.addEncounter(encounter);
            this.encounterService.saveEncounter(encounter);
            return encounter;
        }
    }

    private Provider getProvider(User accountBelongingToUser) {
        Collection candidates = this.providerService.getProvidersByPerson(accountBelongingToUser.getPerson(), false);
        if (candidates.size() == 0) {
            throw new IllegalStateException("User " + accountBelongingToUser.getUsername() + " does not have a Provider account");
        } else if (candidates.size() > 1) {
            throw new IllegalStateException("User " + accountBelongingToUser.getUsername() + " has more than one Provider account");
        } else {
            return (Provider) candidates.iterator().next();
        }
    }

    private Encounter buildEncounter(EncounterType encounterType, Patient patient, Location location, Form form, Date when, List<Obs> obsToCreate, List<Order> ordersToCreate) {
        Encounter encounter = new Encounter();
        encounter.setPatient(patient);
        encounter.setEncounterType(encounterType);
        encounter.setLocation(location);
        encounter.setForm(form);
        encounter.setEncounterDatetime(when);
        Iterator i$;
        if (obsToCreate != null) {
            i$ = obsToCreate.iterator();

            while (i$.hasNext()) {
                Obs order = (Obs) i$.next();
                order.setObsDatetime(new Date());
                encounter.addObs(order);
            }
        }

        if (ordersToCreate != null) {
            i$ = ordersToCreate.iterator();

            while (i$.hasNext()) {
                Order order1 = (Order) i$.next();
                encounter.addOrder(order1);
            }
        }

        return encounter;
    }

    private Visit buildVisit(Patient patient, Location location, Date when) {
        Visit visit = new Visit();
        visit.setPatient(patient);
        visit.setLocation(this.getLocationThatSupportsVisits(location));
        visit.setStartDatetime(when);
        visit.setVisitType(this.emrApiProperties.getAtFacilityVisitType());
        return visit;
    }

    public Location getLocationThatSupportsVisits(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location does not support visits");
        } else {
            return location.hasTag("Visit Location").booleanValue() ? location : this.getLocationThatSupportsVisits(location.getParentLocation());
        }
    }

    @Transactional(
            readOnly = true
    )
    public List<Location> getAllLocationsThatSupportVisits() {
        return this.locationService.getLocationsByTag(this.emrApiProperties.getSupportsVisitsLocationTag());
    }

    public boolean isSuitableVisit(Visit visit, Location location, Date when) {
        return OpenmrsUtil.compare(when, visit.getStartDatetime()) < 0 ? false : (OpenmrsUtil.compareWithNullAsLatest(when, visit.getStopDatetime()) > 0 ? false : this.isSameOrAncestor(visit.getLocation(), location));
    }

    private boolean isSameOrAncestor(Location a, Location b) {
        return a != null && b != null ? a.equals(b) || this.isSameOrAncestor(a, b.getParentLocation()) : a == null && b == null;
    }



    public Encounter getLastEncounter(Patient patient) {
        List byPatient = this.encounterService.getEncountersByPatient(patient);
        return byPatient.size() == 0 ? null : (Encounter) byPatient.get(byPatient.size() - 1);
    }

    public int getCountOfEncounters(Patient patient) {
        return this.encounterService.getEncountersByPatient(patient).size();
    }

    public int getCountOfVisits(Patient patient) {
        return this.visitService.getVisitsByPatient(patient, true, false).size();
    }

    private Set<Location> getChildLocationsRecursively(Location location, Set<Location> foundLocations) {
        if (foundLocations == null) {
            foundLocations = new LinkedHashSet();
        }

        ((Set) foundLocations).add(location);
        if (location.getChildLocations() != null) {
            Iterator i$ = location.getChildLocations().iterator();

            while (i$.hasNext()) {
                Location l = (Location) i$.next();
                ((Set) foundLocations).add(l);
                this.getChildLocationsRecursively(l, (Set) foundLocations);
            }
        }

        return (Set) foundLocations;
    }

    @Transactional
    public void mergePatients(Patient preferred, Patient notPreferred) {


    }

    private void removeAttributeOfUnknownPatient(Patient preferred) {
        PersonAttributeType unknownPatientPersonAttributeType = this.emrApiProperties.getUnknownPatientPersonAttributeType();
        PersonAttribute attribute = preferred.getAttribute(unknownPatientPersonAttributeType);
        if (attribute != null) {
            preferred.removeAttribute(attribute);
            this.patientService.savePatient(preferred);
        }

    }

    public boolean areConsecutiveVisits(List<Integer> visits, Patient patient) {
        if (patient != null && visits != null && visits.size() > 0) {
            List patientVisits = this.visitService.getVisitsByPatient(patient, true, false);
            if (patientVisits != null && patientVisits.size() > 0) {
                ArrayList allVisits = new ArrayList();
                int j = 0;
                Iterator i = patientVisits.iterator();

                while (i.hasNext()) {
                    Visit i$ = (Visit) i.next();
                    allVisits.add(j++, i$.getId());
                }

                if (allVisits.containsAll(visits)) {
                    int var9 = allVisits.indexOf(visits.get(0));
                    if (allVisits.size() - var9 >= visits.size()) {
                        for (Iterator var10 = visits.iterator(); var10.hasNext(); ++var9) {
                            Integer candidateVisit = (Integer) var10.next();
                            if (((Integer) allVisits.get(var9)).compareTo(candidateVisit) != 0) {
                                return false;
                            }
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasVisitDuring(Patient var1, Location var2, Date var3, Date var4) {
        return false;
    }

    public Visit mergeConsecutiveVisits(List<Integer> visits, Patient patient) {
        if (!this.areConsecutiveVisits(visits, patient)) {
            return null;
        } else {
            Visit mergedVisit = this.visitService.getVisit((Integer) visits.get(0));
            if (visits.size() > 1) {
                for (int i = 1; i < visits.size(); ++i) {
                    mergedVisit = this.mergeVisits(mergedVisit, this.visitService.getVisit((Integer) visits.get(i)));
                }
            }

            return mergedVisit;
        }
    }

    public Visit mergeVisits(Visit preferred, Visit nonPreferred) {
        if (OpenmrsUtil.compareWithNullAsEarliest(nonPreferred.getStartDatetime(), preferred.getStartDatetime()) < 0) {
            preferred.setStartDatetime(nonPreferred.getStartDatetime());
        }

        if (preferred.getStopDatetime() != null && OpenmrsUtil.compareWithNullAsLatest(preferred.getStopDatetime(), nonPreferred.getStopDatetime()) < 0) {
            preferred.setStopDatetime(nonPreferred.getStopDatetime());
        }

        if (nonPreferred.getEncounters() != null) {
            Iterator i$ = nonPreferred.getEncounters().iterator();

            while (i$.hasNext()) {
                Encounter e = (Encounter) i$.next();
                e.setPatient(preferred.getPatient());
                preferred.addEncounter(e);
                this.encounterService.saveEncounter(e);
            }
        }

        nonPreferred.setEncounters((Set) null);
        this.visitService.voidVisit(nonPreferred, "EMR - Merge Patients: merged into visit " + preferred.getVisitId());
        this.visitService.saveVisit(preferred);
        return preferred;
    }

    private void addProviders(Encounter encounter, Map<EncounterRole, ? extends Collection<Provider>> providers) {
        Iterator i$ = providers.entrySet().iterator();

        while (i$.hasNext()) {
            Map.Entry entry = (Map.Entry) i$.next();
            EncounterRole encounterRole = (EncounterRole) entry.getKey();
            Iterator i$1 = ((Collection) entry.getValue()).iterator();

            while (i$1.hasNext()) {
                Provider provider = (Provider) i$1.next();
                encounter.addProvider(encounterRole, provider);
            }
        }

    }

    private boolean hasAny(Map<?, ? extends Collection<?>> providers) {
        if (providers == null) {
            return false;
        } else {
            Iterator i$ = providers.values().iterator();

            Collection byType;
            do {
                if (!i$.hasNext()) {
                    return false;
                }

                byType = (Collection) i$.next();
            } while (byType == null || byType.size() <= 0);

            return true;
        }
    }










    public List<Location> getInpatientLocations() {
        return this.locationService.getLocationsByTag(this.emrApiProperties.getSupportsAdmissionLocationTag());
    }
}

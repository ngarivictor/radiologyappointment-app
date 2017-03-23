package org.openmrs.module.appointmentapp;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.openmrs.*;
import org.openmrs.api.EncounterService;
import org.openmrs.api.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * @author Stanslaus Odhaimbo
 *         Created on 23/03/2017.
 */
public class PatientDomainWrapper implements DomainWrapper {
    private Patient patient;
    @Qualifier("newEmrApiProperties")
    @Autowired
    protected EmrApiProperties emrApiProperties;
    @Qualifier("adtService")
    @Autowired(required=false)
    protected AdtService adtService;
    @Qualifier("visitService")
    @Autowired
    protected VisitService visitService;
    @Qualifier("encounterService")
    @Autowired
    protected EncounterService encounterService;

    public PatientDomainWrapper() {
    }

    /** @deprecated */
    @Deprecated
    public PatientDomainWrapper(Patient patient, EmrApiProperties emrApiProperties, AdtService adtService, VisitService visitService, EncounterService encounterService) {
        this.patient = patient;
        this.emrApiProperties = emrApiProperties;
        this.adtService = adtService;
        this.visitService = visitService;
        this.encounterService = encounterService;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setEmrApiProperties(EmrApiProperties emrApiProperties) {
        this.emrApiProperties = emrApiProperties;
    }

    public void setAdtService(AdtService adtService) {
        this.adtService = adtService;
    }

    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    public void setEncounterService(EncounterService encounterService) {
        this.encounterService = encounterService;
    }


    public Patient getPatient() {
        return this.patient;
    }

    public Integer getId() {
        return this.patient.getPatientId();
    }

    public String getGender() {
        return this.patient.getGender();
    }

    public Integer getAge() {
        return this.patient.getAge();
    }

    public Integer getAgeInMonths() {
        if(this.patient.getBirthdate() == null) {
            return null;
        } else {
            Date endDate = this.patient.isDead().booleanValue()?this.patient.getDeathDate():new Date();
            return Integer.valueOf(Months.monthsBetween(new DateTime(this.patient.getBirthdate()), new DateTime(endDate)).getMonths());
        }
    }

    public Integer getAgeInDays() {
        if(this.patient.getBirthdate() == null) {
            return null;
        } else {
            Date endDate = this.patient.isDead().booleanValue()?this.patient.getDeathDate():new Date();
            return Integer.valueOf(Days.daysBetween(new DateTime(this.patient.getBirthdate()), new DateTime(endDate)).getDays());
        }
    }

    public Boolean getBirthdateEstimated() {
        return this.patient.getBirthdateEstimated();
    }

    public Date getBirthdate() {
        return this.patient.getBirthdate();
    }

    public String getTelephoneNumber() {
        String telephoneNumber = null;
        PersonAttributeType type = this.emrApiProperties.getTelephoneAttributeType();
        if(type != null) {
            PersonAttribute attr = this.patient.getAttribute(type);
            if(attr != null && attr.getValue() != null) {
                telephoneNumber = attr.getValue();
            }
        }

        return telephoneNumber;
    }

    public PersonAddress getPersonAddress() {
        return this.patient.getPersonAddress();
    }

    public PatientIdentifier getPrimaryIdentifier() {
        List primaryIdentifiers = this.getPrimaryIdentifiers();
        return primaryIdentifiers.size() == 0?null:(PatientIdentifier)primaryIdentifiers.get(0);
    }

    public List<PatientIdentifier> getPrimaryIdentifiers() {
        return this.patient.getPatientIdentifiers(this.emrApiProperties.getPrimaryIdentifierType());
    }

    public List<PatientIdentifier> getExtraIdentifiers() {
        return this.getExtraIdentifiers((Location)null);
    }

    public List<PatientIdentifier> getExtraIdentifiers(Location location) {
        ArrayList patientIdentifiers = null;
        List types = this.emrApiProperties.getExtraPatientIdentifierTypes();
        if(types != null && types.size() > 0) {
            patientIdentifiers = new ArrayList();
            Iterator i$ = types.iterator();

            label41:
            while(true) {
                PatientIdentifierType type;
                List extraPatientIdentifiers;
                do {
                    if(!i$.hasNext()) {
                        return patientIdentifiers;
                    }

                    type = (PatientIdentifierType)i$.next();
                    extraPatientIdentifiers = this.patient.getPatientIdentifiers(type);
                } while(extraPatientIdentifiers == null);

                Iterator i$1 = extraPatientIdentifiers.iterator();

                while(true) {
                    PatientIdentifier extraPatientIdentifier;
                    do {
                        if(!i$1.hasNext()) {
                            continue label41;
                        }

                        extraPatientIdentifier = (PatientIdentifier)i$1.next();
                    } while(type.getLocationBehavior() != null && type.getLocationBehavior().equals(PatientIdentifierType.LocationBehavior.REQUIRED) && location != null && !Location.isInHierarchy(location, extraPatientIdentifier.getLocation()).booleanValue());

                    patientIdentifiers.add(extraPatientIdentifier);
                }
            }
        } else {
            return patientIdentifiers;
        }
    }

    public Map<PatientIdentifierType, List<PatientIdentifier>> getExtraIdentifiersMappedByType(Location location) {
        HashMap identifierMap = new HashMap();
        List patientIdentifiers = this.getExtraIdentifiers(location);
        PatientIdentifier patientIdentifier;
        if(patientIdentifiers != null) {
            for(Iterator i$ = patientIdentifiers.iterator(); i$.hasNext(); ((List)identifierMap.get(patientIdentifier.getIdentifierType())).add(patientIdentifier)) {
                patientIdentifier = (PatientIdentifier)i$.next();
                if(!identifierMap.containsKey(patientIdentifier.getIdentifierType())) {
                    identifierMap.put(patientIdentifier.getIdentifierType(), new ArrayList());
                }
            }
        }

        return identifierMap;
    }

    public Map<PatientIdentifierType, List<PatientIdentifier>> getExtraIdentifiersMappedByType() {
        return this.getExtraIdentifiersMappedByType((Location)null);
    }

    public Encounter getLastEncounter() {
        return this.adtService.getLastEncounter(this.patient);
    }

    public int getCountOfEncounters() {
        return this.adtService.getCountOfEncounters(this.patient);
    }

    public int getCountOfVisits() {
        return this.adtService.getCountOfVisits(this.patient);
    }

    public List<Encounter> getAllEncounters() {
        return this.encounterService.getEncountersByPatient(this.patient);
    }

    public List<Visit> getAllVisits() {
        return this.visitService.getVisitsByPatient(this.patient, true, false);
    }

    public boolean hasOverlappingVisitsWith(Patient otherPatient) {
        List otherVisits = this.visitService.getVisitsByPatient(otherPatient, true, false);
        List myVisits = this.getAllVisits();
        Iterator i$ = myVisits.iterator();

        while(i$.hasNext()) {
            Visit v = (Visit)i$.next();
            Iterator i$1 = otherVisits.iterator();

            while(i$1.hasNext()) {
                Visit o = (Visit)i$1.next();
                if(this.adtService.visitsOverlap(v, o)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isUnknownPatient() {
        boolean unknownPatient = false;
        PersonAttributeType unknownPatientAttributeType = this.emrApiProperties.getUnknownPatientPersonAttributeType();
        if(this.patient != null) {
            PersonAttribute att = this.patient.getAttribute(unknownPatientAttributeType);
            if(att != null && "true".equals(att.getValue())) {
                unknownPatient = true;
            }
        }

        return unknownPatient;
    }

    public String getFormattedName() {
        return this.getPersonName().getFamilyName() + ", " + this.getPersonName().getGivenName();
    }

    public PersonName getPersonName() {
        Set names = this.patient.getNames();
        if(names != null && names.size() > 0) {
            Iterator i$ = names.iterator();

            PersonName name;
            while(i$.hasNext()) {
                name = (PersonName)i$.next();
                if(name.isPreferred().booleanValue()) {
                    return name;
                }
            }

            i$ = names.iterator();
            if(i$.hasNext()) {
                name = (PersonName)i$.next();
                return name;
            }
        }

        return null;
    }

    public boolean isTestPatient() {
        boolean testPatient = false;
        PersonAttributeType testPatientPersonAttributeType = this.emrApiProperties.getTestPatientPersonAttributeType();
        if(this.patient != null) {
            PersonAttribute att = this.patient.getAttribute(testPatientPersonAttributeType);
            if(att != null && "true".equals(att.getValue())) {
                testPatient = true;
            }
        }

        return testPatient;
    }
}

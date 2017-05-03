package org.openmrs.module.appointmentapp;

import org.apache.commons.lang3.math.NumberUtils;
import org.openmrs.*;
import org.openmrs.module.appointmentapp.utils.ModuleProperties;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

/**
 * Created by mstan on 23/03/2017.
 */
@Component("newEmrApiProperties")
public class EmrApiProperties extends ModuleProperties {
    public EmrApiProperties() {
    }

    public Location getUnknownLocation() {
        return this.getLocationByGlobalProperty("emr.unknownLocation");
    }

    public Provider getUnknownProvider() {
        return this.getProviderByGlobalProperty("emr.unknownProvider");
    }

    public EncounterRole getOrderingProviderEncounterRole() {
        return this.getEncounterRoleByGlobalProperty("emr.orderingProviderEncounterRole");
    }

    public Role getFullPrivilegeLevel() {
        return this.userService.getRole("Privilege Level: Full");
    }

    public EncounterType getCheckInEncounterType() {
        return this.getEncounterTypeByGlobalProperty("emr.checkInEncounterType");
    }

    public EncounterRole getCheckInClerkEncounterRole() {
        return this.getEncounterRoleByGlobalProperty("emr.checkInClerkEncounterRole");
    }

    public EncounterType getVisitNoteEncounterType() {
        try {
            return this.getEncounterTypeByGlobalProperty("emr.visitNoteEncounterType");
        } catch (IllegalStateException var2) {
            return this.getConsultEncounterType();
        }
    }

    /** @deprecated */
    @Deprecated
    public EncounterType getConsultEncounterType() {
        return this.getEncounterTypeByGlobalProperty("emr.consultEncounterType");
    }

    public EncounterRole getClinicianEncounterRole() {
        return this.getEncounterRoleByGlobalProperty("emr.clinicianEncounterRole");
    }

    public EncounterType getAdmissionEncounterType() {
        return this.getEncounterTypeByGlobalProperty("emr.admissionEncounterType", false);
    }

    public EncounterType getExitFromInpatientEncounterType() {
        return this.getEncounterTypeByGlobalProperty("emr.exitFromInpatientEncounterType", false);
    }

    public EncounterType getTransferWithinHospitalEncounterType() {
        return this.getEncounterTypeByGlobalProperty("emr.transferWithinHospitalEncounterType", false);
    }

    public Form getAdmissionForm() {
        return this.getFormByGlobalProperty("emr.admissionForm");
    }

    public Form getDischargeForm() {
        return this.getFormByGlobalProperty("emr.exitFromInpatientForm");
    }

    public Form getTransferForm() {
        return this.getFormByGlobalProperty("emr.transferWithinHospitalForm");
    }

    public int getVisitExpireHours() {
        return NumberUtils.toInt(this.getGlobalProperty("emrapi.visitExpireHours", false), 12);
    }

    public VisitType getAtFacilityVisitType() {
        return this.getVisitTypeByGlobalProperty("emr.atFacilityVisitType");
    }

    public LocationTag getSupportsVisitsLocationTag() {
        return this.locationService.getLocationTagByName("Visit Location");
    }

    public LocationTag getSupportsLoginLocationTag() {
        return this.locationService.getLocationTagByName("Login Location");
    }

    public LocationTag getSupportsAdmissionLocationTag() {
        return this.locationService.getLocationTagByName("Admission Location");
    }

    public LocationTag getSupportsTransferLocationTag() {
        return this.locationService.getLocationTagByName("Transfer Location");
    }

    public PersonAttributeType getTestPatientPersonAttributeType() {
        PersonAttributeType type = null;
        type = this.personService.getPersonAttributeTypeByUuid("4f07985c-88a5-4abd-aa0c-f3ec8324d8e7");
        if(type == null) {
            throw new IllegalStateException("Configuration required: Test Patient Attribute UUID");
        } else {
            return type;
        }
    }

    public PersonAttributeType getTelephoneAttributeType() {
        PersonAttributeType type = null;
        type = this.personService.getPersonAttributeTypeByName("Telephone Number");
        if(type == null) {
            throw new IllegalStateException("Configuration required: Telephone Number");
        } else {
            return type;
        }
    }

    public PersonAttributeType getUnknownPatientPersonAttributeType() {
        PersonAttributeType type = null;
        type = this.personService.getPersonAttributeTypeByName("Unknown patient");
        if(type == null) {
            throw new IllegalStateException("Configuration required: Unknown patient");
        } else {
            return type;
        }
    }

    public PatientIdentifierType getPrimaryIdentifierType() {
        return this.getPatientIdentifierTypeByGlobalProperty("emr.primaryIdentifierType", true);
    }

    public List<PatientIdentifierType> getExtraPatientIdentifierTypes() {
        return this.getPatientIdentifierTypesByGlobalProperty("emr.extraPatientIdentifierTypes", false);
    }

    public DiagnosisMetadata getDiagnosisMetadata() {
        return new DiagnosisMetadata(this.conceptService, this.getEmrApiConceptSource());
    }

    public List<ConceptSource> getConceptSourcesForDiagnosisSearch() {
        ConceptSource icd10 = this.conceptService.getConceptSourceByName("ICD-10-WHO");
        return icd10 != null? Arrays.asList(new ConceptSource[]{icd10}):null;
    }

    public ConceptSource getEmrApiConceptSource() {
        return this.conceptService.getConceptSourceByName("org.openmrs.module.emrapi");
    }

    protected Concept getEmrApiConceptByMapping(String code) {
        return this.getSingleConceptByMapping(this.getEmrApiConceptSource(), code);
    }

    public Concept getUnknownCauseOfDeathConcept() {
        return this.getEmrApiConceptByMapping("Unknown Cause of Death");
    }

    public Concept getAdmissionDecisionConcept() {
        return this.getEmrApiConceptByMapping("Admission Decision");
    }

    public Concept getDenyAdmissionConcept() {
        return this.getEmrApiConceptByMapping("Deny Admission");
    }

    public List<PatientIdentifierType> getIdentifierTypesToSearch() {
        ArrayList types = new ArrayList();
        types.add(this.getPrimaryIdentifierType());
        List extraPatientIdentifierTypes = this.getExtraPatientIdentifierTypes();
        if(extraPatientIdentifierTypes != null && extraPatientIdentifierTypes.size() > 0) {
            types.addAll(extraPatientIdentifierTypes);
        }

        return types;
    }

    public Collection<Concept> getDiagnosisSets() {
        String gp = this.getGlobalProperty("emr.concept.diagnosisSetOfSets", false);
        if(StringUtils.hasText(gp)) {
            Concept setOfSets = this.conceptService.getConceptByUuid(gp);
            if(setOfSets == null) {
                throw new IllegalStateException("Configuration required: emr.concept.diagnosisSetOfSets");
            } else {
                return setOfSets.getSetMembers();
            }
        } else {
            return null;
        }
    }

    public Collection<Concept> getNonDiagnosisConceptSets() {
        Collection concepts = this.getConceptsByGlobalProperty("emrapi.nonDiagnosisConceptSets");
        Iterator i$ = concepts.iterator();

        Concept concept;
        do {
            if(!i$.hasNext()) {
                return concepts;
            }

            concept = (Concept)i$.next();
        } while(concept.isSet().booleanValue());

        throw new IllegalStateException("Invalid configuration: concept \'" + concept.getUuid() + "\' defined in " + "emrapi.nonDiagnosisConceptSets" + " is not a concept set");
    }

    public Collection<Concept> getSuppressedDiagnosisConcepts() {
        return this.getConceptsByGlobalProperty("emrapi.suppressedDiagnosisConcepts");
    }

    public ConceptMapType getSameAsConceptMapType() {
        return this.conceptService.getConceptMapTypeByUuid("35543629-7d8c-11e1-909d-c80aa9edcf4e");
    }

    public ConceptMapType getNarrowerThanConceptMapType() {
        return this.conceptService.getConceptMapTypeByUuid("43ac5109-7d8c-11e1-909d-c80aa9edcf4e");
    }

    public Integer getLastViewedPatientSizeLimit() {
        String limit = this.administrationService.getGlobalProperty("emrapi.lastViewedPatientSizeLimit");
        if(StringUtils.hasText(limit)) {
            try {
                return Integer.valueOf(limit);
            } catch (NumberFormatException var3) {
                ;
            }
        }

        return Integer.valueOf(50);
    }

    public File getPersonImageDirectory() {
        String personImagesDir = this.getGlobalProperty("emr.personImagesDirectory", false);
        if(personImagesDir == null || personImagesDir.isEmpty()) {
            File appDataDirectory = new File(OpenmrsUtil.getApplicationDataDirectory());
            personImagesDir = appDataDirectory.getAbsolutePath() + "/person_images";
        }

        return new File(personImagesDir);
    }
}

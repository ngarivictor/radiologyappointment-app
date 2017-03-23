package org.openmrs.module.appointmentapp;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mstan on 23/03/2017.
 */
public class DispositionDescriptor extends ConceptSetDescriptor {
    private Concept dispositionSetConcept;
    private Concept dispositionConcept;
    private Concept admissionLocationConcept;
    private Concept internalTransferLocationConcept;
    private Concept dateOfDeathConcept;

    public DispositionDescriptor(ConceptService conceptService) {
        this.setup(conceptService, "org.openmrs.module.emrapi", ConceptSetDescriptorField.required("dispositionSetConcept", "Disposition Concept Set"), new ConceptSetDescriptorField[]{ConceptSetDescriptorField.required("dispositionConcept", "Disposition"), ConceptSetDescriptorField.optional("admissionLocationConcept", "Admission Location"), ConceptSetDescriptorField.optional("internalTransferLocationConcept", "Internal Transfer Location"), ConceptSetDescriptorField.optional("dateOfDeathConcept", "Date of Death")});
    }

    public DispositionDescriptor() {
    }

    public Concept getDispositionSetConcept() {
        return this.dispositionSetConcept;
    }

    public void setDispositionSetConcept(Concept dispositionSetConcept) {
        this.dispositionSetConcept = dispositionSetConcept;
    }

    public Concept getDispositionConcept() {
        return this.dispositionConcept;
    }

    public void setDispositionConcept(Concept dispositionConcept) {
        this.dispositionConcept = dispositionConcept;
    }

    public Concept getAdmissionLocationConcept() {
        return this.admissionLocationConcept;
    }

    public void setAdmissionLocationConcept(Concept admissionLocationConcept) {
        this.admissionLocationConcept = admissionLocationConcept;
    }

    public Concept getInternalTransferLocationConcept() {
        return this.internalTransferLocationConcept;
    }

    public void setInternalTransferLocationConcept(Concept internalTransferLocationConcept) {
        this.internalTransferLocationConcept = internalTransferLocationConcept;
    }

    public Concept getDateOfDeathConcept() {
        return this.dateOfDeathConcept;
    }

    public void setDateOfDeathConcept(Concept dateOfDeathConcept) {
        this.dateOfDeathConcept = dateOfDeathConcept;
    }

    public Obs buildObsGroup(Disposition disposition, EmrConceptService emrConceptService) {
        Obs dispoObs = new Obs();
        dispoObs.setConcept(this.dispositionConcept);
        dispoObs.setValueCoded(emrConceptService.getConcept(disposition.getConceptCode()));
        Obs group = new Obs();
        group.setConcept(this.dispositionSetConcept);
        group.addGroupMember(dispoObs);
        return group;
    }

    public boolean isDisposition(Obs obs) {
        return obs.getConcept().equals(this.dispositionSetConcept);
    }

    public Obs getDispositionObs(Obs obsGroup) {
        return this.findMember(obsGroup, this.dispositionConcept);
    }

    public Obs getAdmissionLocationObs(Obs obsGroup) {
        return this.findMember(obsGroup, this.admissionLocationConcept);
    }

    public Obs getInternalTransferLocationObs(Obs obsGroup) {
        return this.findMember(obsGroup, this.internalTransferLocationConcept);
    }

    public Obs getDateOfDeathObs(Obs obsGroup) {
        return this.findMember(obsGroup, this.dateOfDeathConcept);
    }

    public Location getAdmissionLocation(Obs obsGroup, LocationService locationService) {
        Obs admissionLocationObs = this.getAdmissionLocationObs(obsGroup);
        return admissionLocationObs != null?locationService.getLocation(Integer.valueOf(admissionLocationObs.getValueText())):null;
    }

    public Location getInternalTransferLocation(Obs obsGroup, LocationService locationService) {
        Obs transferLocationObs = this.getInternalTransferLocationObs(obsGroup);
        return transferLocationObs != null?locationService.getLocation(Integer.valueOf(transferLocationObs.getValueText())):null;
    }

    public Date getDateOfDeath(Obs obsGroup) {
        Obs dateOfDeathObs = this.getDateOfDeathObs(obsGroup);
        return dateOfDeathObs != null?dateOfDeathObs.getValueDate():null;
    }

    public List<Obs> getAdditionalObs(Obs obsGroup) {
        ArrayList notDisposition = new ArrayList();
        if(obsGroup.hasGroupMembers()) {
            Iterator i$ = obsGroup.getGroupMembers().iterator();

            while(i$.hasNext()) {
                Obs candidate = (Obs)i$.next();
                if(!candidate.getConcept().equals(this.dispositionConcept) && !candidate.getConcept().equals(this.admissionLocationConcept) && !candidate.getConcept().equals(this.internalTransferLocationConcept) && !candidate.getConcept().equals(this.dateOfDeathConcept)) {
                    notDisposition.add(candidate);
                }
            }
        }

        return notDisposition;
    }
}

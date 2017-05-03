package org.openmrs.module.appointmentapp;

import org.openmrs.Obs;

import java.util.List;

/**
 * Created by mstan on 23/03/2017.
 */
public interface DispositionService {
    void setDispositionConfig(String var1);

    DispositionDescriptor getDispositionDescriptor();

    List<Disposition> getDispositions();

    Disposition getDispositionByUniqueId(String var1);

    List<Disposition> getDispositionsByType(DispositionType var1);

    Disposition getDispositionFromObs(Obs var1);

    Disposition getDispositionFromObsGroup(Obs var1);
}
package org.openmrs.module.appointmentapp;

/**
 * Created by mstan on 23/03/2017.
 */
public interface Evaluated<T extends Definition> {
    T getDefinition();


}

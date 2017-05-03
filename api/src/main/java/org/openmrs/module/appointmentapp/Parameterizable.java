package org.openmrs.module.appointmentapp;

import org.openmrs.OpenmrsMetadata;

import java.util.List;

/**
 * Created by mstan on 23/03/2017.
 */
public interface Parameterizable extends OpenmrsMetadata {
    List<Parameter> getParameters();

    Parameter getParameter(String var1);

    void addParameter(Parameter var1);

    void removeParameter(Parameter var1);

    void removeParameter(String var1);
}

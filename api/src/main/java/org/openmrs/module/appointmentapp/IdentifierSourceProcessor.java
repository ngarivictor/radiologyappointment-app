package org.openmrs.module.appointmentapp;

import java.util.List;

/**
 * Created by mstan on 23/03/2017.
 */
public interface IdentifierSourceProcessor {
    List<String> getIdentifiers(IdentifierSource var1, int var2);
}

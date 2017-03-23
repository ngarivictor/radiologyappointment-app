package org.openmrs.module.appointmentapp;

import java.util.List;
import java.util.Map;

/**
 * Created by mstan on 23/03/2017.
 */
public interface NameSupportCompatibility {

    List<List<Map<String, String>>> getLines();

    String getLayoutToken();

    Map<String, String> getNameMappings();
}
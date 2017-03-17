package org.openmrs.module.appointmentapp;

import org.openmrs.LocationTag;
import org.openmrs.module.appointmentapp.utils.ModuleProperties;
import org.springframework.stereotype.Component;

@Component("appointmentSchedulingUIProperties")
public class AppointmentSchedulingUIProperties extends ModuleProperties {

    public LocationTag getSupportsAppointmentsTag() {
        return locationService.getLocationTagByName(AppointmentSchedulingUIConstants.LOCATION_TAG_SUPPORTS_APPOINTMENTS);
    }

}

package org.openmrs.module.appointmentapp.page.controller;

import org.openmrs.LocationTag;
import org.openmrs.module.appointmentapp.AppointmentSchedulingUIProperties;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;

public class ScheduleProvidersPageController {


    public Object controller(PageModel model,
                             UiSessionContext uiSessionContext,
                             @SpringBean("appointmentSchedulingUIProperties")AppointmentSchedulingUIProperties properties) {

        LocationTag supportsAppointmentsTag =  properties.getSupportsAppointmentsTag();
        model.addAttribute("supportsAppointmentsTagUuid",
                supportsAppointmentsTag != null ? supportsAppointmentsTag.getUuid() : "");
        model.addAttribute("sessionLocationUuid", uiSessionContext != null && uiSessionContext.getSessionLocation() != null ?
                uiSessionContext.getSessionLocation().getUuid() : "");

        return null;
    }
}

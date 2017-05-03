package org.openmrs.module.appointmentapp.fragment.controller;

import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneWarningFragmentController {

    public void controller(FragmentModel model) {
        TimeZone tz = Calendar.getInstance().getTimeZone();
        model.addAttribute("serverTimeZone", tz.getDisplayName());
        model.addAttribute("serverTimeZoneOffset", tz.getOffset(new Date().getTime()) / 1000 / 60);
    }

}

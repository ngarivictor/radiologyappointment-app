package org.openmrs.module.appointmentapp;

import org.openmrs.module.appointmentapp.utils.EmrApiConstants;

public class AppointmentSchedulingUIConstants {

    public static final String TIME_FORMAT = "hh:mm aa";

    public static final String PRIVILEGE_BOOK_APPOINTMENTS = EmrApiConstants.PRIVILEGE_PREFIX_TASK + "appointmentschedulingui.bookAppointments";

    public static final String PRIVILEGE_OVERBOOK_APPOINTMENTS = EmrApiConstants.PRIVILEGE_PREFIX_TASK + "appointmentschedulingui.overbookAppointments";

    public static final String LOCATION_TAG_SUPPORTS_APPOINTMENTS = "Appointment Location";

    public static final String APPOINTMENT_CHECK_IN_TAG_NAME = "appointmentCheckIn";

    public static final String DAILY_SCHEDULED_APPOINTMENT_DATA_SET_DEFINITION_UUID = "c1bf0730-e69e-11e3-ac10-0800200c9a66";

    public static final String GP_RECENT_DIAGNOSIS_PERIOD_IN_DAYS = "coreapps.recentDiagnosisPeriodInDays";

    public static final String GP_SEARCH_DELAY_SHORT = "coreapps.searchDelayShort";
    public static final String GP_SEARCH_DELAY_LONG = "coreapps.searchDelayLong";
    public static final String GP_VISITS_PAGE_WITH_SPECIFIC_URL = "coreapps.visitsPageWithSpecificVisitUrl";
    public static final String GP_DEFAULT_PATIENT_IDENTIFIER_LOCATION = "coreapps.defaultPatientIdentifierLocation";
    public static final String GP_VISITS_PAGE_URL = "coreapps.visitsPageUrl";
    public static final String GP_DASHBOARD_URL = "coreapps.dashboardUrl";

}

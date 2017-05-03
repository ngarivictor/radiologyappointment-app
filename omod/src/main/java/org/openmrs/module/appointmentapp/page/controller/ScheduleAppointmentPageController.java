package org.openmrs.module.appointmentapp.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.module.appointmentapp.AppointmentSchedulingUIConstants;
import org.openmrs.module.appointmentapp.PatientDomainWrapper;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.InjectBeans;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.Redirect;
import org.springframework.web.bind.annotation.RequestParam;

public class ScheduleAppointmentPageController {


    protected final Log log = LogFactory.getLog(getClass());

    public Object controller(@RequestParam("patientId") Patient patient,
                             PageModel model, UiSessionContext uiSessionContext,
                             UiUtils ui,
                             @InjectBeans PatientDomainWrapper patientDomainWrapper,
                             @RequestParam(value="returnUrl", required = false) String returnUrl,
                             @RequestParam(value = "breadcrumbOverride", required = false) String breadcrumbOverride,
                             @SpringBean("appointmentService") AppointmentService appointmentService) {

        // TODO stole this from core apps patient dashboard--does it do what we want it to do?
        if (patient.isVoided() || patient.isPersonVoided()) {
            return new Redirect("coreapps", "patientdashboard/deletedPatient", "patientId=" + patient.getId());
        }

        patientDomainWrapper.setPatient(patient);
        model.addAttribute("patient", patientDomainWrapper);
        model.addAttribute("canBook", uiSessionContext.getCurrentUser().hasPrivilege(AppointmentSchedulingUIConstants.PRIVILEGE_BOOK_APPOINTMENTS));
        model.addAttribute("returnUrl", returnUrl);
        return null;
    }
}

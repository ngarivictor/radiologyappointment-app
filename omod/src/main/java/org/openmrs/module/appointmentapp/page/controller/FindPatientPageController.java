package org.openmrs.module.appointmentapp.page.controller;

import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 */
public class FindPatientPageController {
	
	/**
	 * This page is built to be shared across multiple apps. To use it, you must pass an "app"
	 * request parameter, which must be the id of an existing app that is an instance of
	 * coreapps.template.findPatient
	 * 
	 * @param model
	 * @param sessionContext
	 */
	public void get(PageModel model, UiSessionContext sessionContext,
					@RequestParam(value = "appointmentBlockId", required = false) String appointmentBlockId,
                    UiUtils ui) {
        model.addAttribute("afterSelectedUrl", "/appointmentapp/scheduleAppointment.page?patientId={{patientId}}&appointmentBlock="+appointmentBlockId);
        model.addAttribute("heading", "");
        model.addAttribute("label", "");
        model.addAttribute("appointmentBlockId", appointmentBlockId);

	}

}

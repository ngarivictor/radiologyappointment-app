package org.openmrs.module.appointmentapp.page.controller;

import org.openmrs.Location;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentBlock;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.appointmentscheduling.validator.AppointmentTypeValidator;
import org.openmrs.module.appointmentscheduling.web.AppointmentBlockEditor;
import org.openmrs.module.appointmentscheduling.web.AppointmentTypeEditor;
import org.openmrs.module.appointmentscheduling.web.ProviderEditor;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

public class AppointmentBlockFormPageController {


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(AppointmentType.class, new AppointmentTypeEditor());
        binder.registerCustomEditor(AppointmentBlock.class, new AppointmentBlockEditor());
        binder.registerCustomEditor(Provider.class, new ProviderEditor());
    }

    public void get(PageModel model,
                    UiUtils ui, HttpServletRequest request,
//                    @RequestParam(value = "fromDate", required = false) Date fromDate,
//                    @RequestParam(value = "toDate", required = false) Date toDate,
//                    @RequestParam(value = "redirectedFrom", required = false) String redirectedFrom,
                    @SpringBean("appointmentService") AppointmentService service) throws Exception {


    }


    public String post(PageModel model, HttpServletRequest request,
                       @ModelAttribute("appointmentType") @BindParams AppointmentType appointmentType,
                       BindingResult errors,
                       @RequestParam(value = "action", required = false) String action,
                       @RequestParam(value = "locationId", required = false) Location location,
                       @RequestParam(value = "chosenType", required = false) Integer appointmentTypeId,
                       @RequestParam(value = "chosenProvider", required = false) Integer providerId,
                       @RequestParam(value = "fromDate", required = false) Long fromDate,
                       @RequestParam(value = "toDate", required = false) Long toDate,
                       @RequestParam(value = "appointmentBlockId", required = false) Integer appointmentBlockId,
                       @SpringBean("appointmentService") AppointmentService appointmentService,
                       @SpringBean("appointmentTypeValidator") AppointmentTypeValidator appointmentTypeValidator) {
        if (Context.isAuthenticated()) {
            //Updating session variables
            Calendar cal = OpenmrsUtil.getDateTimeFormat(Context.getLocale()).getCalendar();
            cal.setTimeInMillis(fromDate);
            Date fromDateAsDate = cal.getTime();
            cal.setTimeInMillis(toDate);
            Date toDateAsDate = cal.getTime();
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("chosenLocation", location);
            httpSession.setAttribute("lastLocale", Context.getLocale());
            httpSession.setAttribute("chosenProvider", providerId);
            httpSession.setAttribute("chosenType", appointmentTypeId);
            //If the user wants to add new appointment block (clicked on a day)
            if (action != null && action.equals("addNewAppointmentBlock")) {
                String getRequest = "";
                //Fill the request from the user with selected date and forward it to appointmentBlockForm
                getRequest += "fromDate=" + Context.getDateTimeFormat().format(fromDateAsDate);
                if (toDate != null && !toDate.equals(fromDate)) { //If the fromDate is not the same as toDate (not a day click on month view)
                    getRequest += "&toDate=" + Context.getDateTimeFormat().format(toDateAsDate);
                }
                getRequest += "&redirectedFrom=appointmentBlockCalendar.list";
                return "redirect:appointmentBlockForm.form?" + getRequest;
            }
            //If the user wants to change the view to table view
            else if (action != null && action.equals("changeToTableView")) {
                return "redirect:appointmentBlockList.list";
            }
            //If the user wants to edit an existing appointment block (clicked on an event)
            else if (action != null && action.equals("editAppointmentBlock")) {
                return "redirect:appointmentBlockForm.form?appointmentBlockId=" + appointmentBlockId
                        + "&redirectedFrom=appointmentBlockCalendar.list";
            }
        }

        return null;

    }

}

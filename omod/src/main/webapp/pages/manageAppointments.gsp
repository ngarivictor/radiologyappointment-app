<%
    ui.decorateWith("appui", "standardEmrPage", [title: "Manage Appointment Blocks"])
    ui.includeCss("appointmentapp", "radiology.css")
    ui.includeJavascript("uicommons", "moment.js")
    ui.includeJavascript("appointmentapp", "jquery.form.js")
    ui.includeJavascript("appointmentapp", "jq.browser.select.js")

    def angularLocale = context.locale.toString().toLowerCase();

    ui.decorateWith("appui", "standardEmrPage")

    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("uicommons", "i18n/angular-locale_" + angularLocale + ".js")
    ui.includeJavascript("uicommons", "angular-resource.min.js")
    ui.includeJavascript("uicommons", "angular-ui/ui-bootstrap-tpls-0.13.0.min.js")
    ui.includeJavascript("uicommons", "angular-ui/calendar.js")

    ui.includeJavascript("uicommons", "fullcalendar/fullcalendar.min.js")
    ui.includeJavascript("uicommons", "fullcalendar/gcal.js")
    ui.includeCss("uicommons", "fullcalendar/fullcalendar.css")
    ui.includeJavascript("uicommons", "moment-with-locales.min.js")

    ui.includeJavascript("uicommons", "emr.js")

    ui.includeJavascript("uicommons", "angular-common.js")
    ui.includeJavascript("uicommons", "services/providerService.js")
    ui.includeJavascript("uicommons", "services/locationService.js")
    ui.includeJavascript("uicommons", "rest/restUtils.js")

    ui.includeJavascript("appointmentapp", "app.js")
    ui.includeJavascript("appointmentapp", "controllers/selectMultipleAppointmentTypesController.js")
    ui.includeJavascript("appointmentapp", "controllers/scheduleProvidersController.js")
    ui.includeJavascript("appointmentapp", "directives/selectMultipleAppointmentTypesDirective.js")

    ui.includeJavascript("appointmentapp", "resources/appointmentResources.js")
    ui.includeJavascript("appointmentapp", "services/appointmentService.js")

    ui.includeJavascript("appointmentapp", "qtip/jquery.qtip.min.js")
    ui.includeCss("appointmentapp", "qtip/jquery.qtip.min.css")

    ui.includeCss("appointmentapp", "scheduleProviders.css")
    ui.includeCss("appointmentapp", "selectMultipleAppointmentTypes.css")
%>

<%= ui.includeFragment("appui", "messages", [ codes: [
        'appointmentschedulingui.scheduleProviders.errorSavingAppointmentBlock',
        'appointmentschedulingui.scheduleProviders.errorDeletingAppointmentBlock',
        'uicommons.location',
        'uicommons.provider',
        'appointmentschedulingui.scheduleProviders.startTimeMustBeBeforeEndTime',
        'appointmentschedulingui.calendar.month',
        'appointmentschedulingui.calendar.basicDay',
        'appointmentschedulingui.calendar.basicWeek',
        'appointmentschedulingui.calendar.today'
].flatten()
]) %>



<style>
.new-patient-header .identifiers {
    margin-top: 5px;
}

.name {
    color: #f26522;
}

#inline-tabs {
    background: #f9f9f9 none repeat scroll 0 0;
}

#breadcrumbs a, #breadcrumbs a:link, #breadcrumbs a:visited {
    text-decoration: none;
}

form fieldset, .form fieldset {
    padding: 10px;
    width: 97.4%;
}

#referred-date label,
#accepted-date label,
#accepted-date-edit label {
    display: none;
}

form input[type="text"],
form input[type="number"] {
    width: 92%;
}

form select {
    width: 100%;
}

form input:focus, form select:focus {
    outline: 2px none #007fff;
    border: 1px solid #777;
}

.add-on {
    color: #f26522;
    float: right;
    left: auto;
    margin-left: -31px;
    margin-top: 8px;
    position: absolute;
}

.webkit .add-on {
    color: #F26522;
    float: right;
    left: auto;
    margin-left: -31px;
    margin-top: -27px !important;
    position: relative !important;
}

.toast-item {
    background: #333 none repeat scroll 0 0;
}

#queue table, #worklist table, #results table {
    font-size: 14px;
    margin-top: 10px;
}

#refresh {
    border: 1px none #88af28;
    color: #fff !important;
    float: right;
    margin-right: -10px;
    margin-top: 5px;
}

#refresh a i {
    font-size: 12px;
}

form label, .form label {
    color: #028b7d;
}

.col5 {
    width: 65%;
}

.col5 button {
    float: right;
    margin-left: 3px;
    margin-right: 0;
    min-width: 180px;
}

form input[type="checkbox"] {
    margin: 5px 8px 8px;
}

.toast-item-image {
    top: 25px;
}

.ui-widget-content a {
    color: #007fff;
}

.accepted {
    color: #f26522;
}

#modal-overlay {
    background: #000 none repeat scroll 0 0;
    opacity: 0.4 !important;
}

.dialog-data {
    display: inline-block;
    width: 120px;
    color: #028b7d;
}

.inline {
    display: inline-block;
}

#reschedule-date label,
#reorder-date label {
    display: none;
}

#reschedule-date-display,
#reorder-date-display {
    min-width: 1px;
    width: 235px;
}

.dialog {
    display: none;
}

.dialog select {
    display: inline;
    width: 255px;
}

.dialog select option {
    font-size: 1em;
}

#modal-overlay {
    background: #000 none repeat scroll 0 0;
    opacity: 0.4 !important;
}
</style>
<script type="text/javascript">
    var supportsAppointmentsTagUuid = '${ supportsAppointmentsTagUuid }';
    var sessionLocationUuid = '${ sessionLocationUuid }'

</script>

<div class="clear"></div>
<%=ui.includeFragment("appointmentapp", "timeZoneWarning")%>
<div id="main-div">
    <div class="container">
        <div class="example">
            <ul id="breadcrumbs">
                <li>
                    <a href="${ui.pageLink('referenceapplication', 'home')}">
                        <i class="icon-home small"></i>
                    </a>
                </li>

                <li>
                    <a href="${ui.pageLink('appointmentapp', 'main')}">
                        <i class="icon-chevron-right link"></i>
                        Appointment Scheduling
                    </a>
                </li>
                <li>
                    <i class="icon-chevron-right link"></i>
                    ${ ui.message("appointmentapp.ManageAppointments.title") }
                </li>
            </ul>
        </div>
    </div>

    <div class="patient-header new-patient-header">
        <div class="demographics">
            <h1 class="name" style="border-bottom: 1px solid #ddd;">
                <span>&nbsp;${ ui.message("appointmentapp.ManageAppointments.title") } &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>
            </h1>
        </div>

        <div class="show-icon">
            &nbsp;
        </div>

        <div>
            <div class="schedule-providers" ng-app="appointmentscheduling.scheduleProviders" ng-controller="ScheduleProvidersCtrl" ng-cloak>

                <div ng-show="showCalendar">
                    <div id="filter-parameters">
                        <div id="filter-location" class="inline-box">
                            <p>${ ui.message("uicommons.location") }</p>
                            <select ng-model="locationFilter" ng-options="l.display for l in locations" ng-change="refreshCalendarEvents()">
                            </select>
                        </div>

                        <div id="filter-provider" class="inline-box">
                            <p>${ ui.message("uicommons.provider") }</p>
                            <input type="text"
                                   ng-model="providerFilter"
                                   typeahead="provider as provider.person.display for provider in getProviders(\$viewValue) | filter: \$viewValue | limitTo:8"
                                   typeahead-on-select="refreshCalendarEvents()">
                            <i class="icon-remove small add-on" ng-click="providerFilter=''" ></i>
                        </div>

                        <selectmultipleappointmenttypes headermessage='${ ui.message("appointmentschedulingui.appointmenttypes") }'
                                                        viewall='${ ui.message("appointmentschedulingui.scheduleAppointment.viewAllTypes") }'
                                                        closemessage='${ ui.message("uicommons.close")}'
                                                        senderid = 'viewAppointmentBlock'
                                                        placeholdermessage = '${ ui.message("appointmentschedulingui.scheduleProviders.selectMultiplePlaceholder") }'
                                                        class="inline-box">

                        </selectmultipleappointmenttypes>
                    </div>

                    <div id="calendar" ui-calendar="uiConfig.calendar" calendar="calendar" ng-model="appointmentBlocksSource"></div>
                </div>

                <div id="appointment-block-form" ng-show="showAppointmentBlockForm">

                    <div id="appointment-block-form-error" class="note-container" ng-repeat="message in appointmentBlockFormErrorMessages">
                        <div class="note error">
                            <div class="text">
                                <i class="icon-remove medium"></i>
                                {{ message }}
                            </div>
                        </div>
                    </div>

                    <div id="appointment-block-form-header">
                        <h1 ng-show="!appointmentBlock.uuid">
                            ${ ui.message("appointmentschedulingui.scheduleProviders.createAppointmentBlock") }
                        </h1>

                        <h1 ng-show="appointmentBlock.uuid">
                            ${ ui.message("appointmentschedulingui.scheduleProviders.editAppointmentBlock") }
                        </h1>
                    </div>

                    <div id="appointment-block-form-provider-and-location-and-date">
                        <div id="select-location" class="inline-box">
                            <p>${ ui.message("uicommons.location") }</p>
                            <select ng-change="updateSaveButton()" ng-model="appointmentBlock.location" ng-options="l.display for l in locations track by l.uuid">
                            </select>
                        </div>

                        <div id="select-provider" class="inline-box">
                            <p>${ ui.message("uicommons.provider") }</p>
                            <input type="text"  ng-change="updateSaveButton()" ng-model="appointmentBlock.provider" typeahead="provider as provider.person.display for provider in getProviders(\$viewValue) | filter: \$viewValue | limitTo:8" >
                        </div>

                        <div id="select-date" class="inline-box">
                            <p>${ ui.message("uicommons.date") }</p>
                            <span class="angular-datepicker">
                                <input ng-change="updateSaveButton()" type="text" ng-model="appointmentBlock.startDate" show-weeks="false" datepicker-popup="dd-MMMM-yyyy" readonly/>
                            </span>
                        </div>
                    </div>

                    <div id="appointment-block-form-time">
                        <div id="start-time" class="inline-box">
                            <p>${ ui.message("appointmentschedulingui.startTime") }</p>
                            <timepicker ng-change="updateSaveButton()" ng-model="appointmentBlock.startDate" minute-step="15" />
                        </div>

                        <div id="end-time" class="inline-box">
                            <p>${ ui.message("appointmentschedulingui.endTime") }</p>
                            <timepicker ng-change="updateSaveButton()" ng-model="appointmentBlock.endDate"  minute-step="15" />
                        </div>
                    </div>

                    <selectmultipleappointmenttypes headermessage='${ ui.message("appointmentschedulingui.appointmenttypes") }'
                                                    viewall='${ ui.message("appointmentschedulingui.scheduleAppointment.viewAllTypes") }'
                                                    closemessage='${ ui.message("uicommons.close")}'
                                                    senderid = 'createAppointmentBlock'
                                                    placeholdermessage = '${ ui.message("appointmentschedulingui.scheduleProviders.selectMultiplePlaceholder") }'
                                                    class="inline-box"></selectmultipleappointmenttypes>

                    <div id="appointment-block-form-buttons">
                        <button class="cancel" ng-click="showAppointmentBlockForm=false;showCalendar=true;refreshCalendarEvents()"> ${ ui.message("uicommons.cancel") }</button>
                        <button class="confirm" ng-click="saveAppointmentBlock()" ng-disabled="disableSaveButton || !appointmentBlock.location || !appointmentBlock.startDate || !appointmentBlock.endDate || appointmentBlock.types.length == 0">
                            ${ ui.message("uicommons.save") }</button>
                    </div>

                </div>

                <div id="delete-appointment-block-modal" class="dialog" style="display:none">
                    <div class="dialog-header">
                        <h3>${ ui.message("appointmentschedulingui.scheduleProviders.deleteAppointmentBlock") }</h3>
                    </div>
                    <div class="dialog-content">
                        <p>${ ui.message("appointmentschedulingui.scheduleProviders.deleteAppointmentBlockMessage") }</p>
                        <p id="delete-appointment-block-modal-buttons"><span class="button cancel">${ ui.message("uicommons.cancel") }</span> <span class="button confirm"> ${ ui.message("uicommons.delete") }</span></p>
                    </div>
                </div>

                <div id="tooltip" class="hidden" >
                    <p>{{ appointmentBlock.startDate | date: 'MMM d' }}, {{ appointmentBlock.startDate | date: 'hh:mm a' }} - {{ appointmentBlock.endDate | date: 'hh:mm a' }}</p>
                    <p>${ ui.message('uicommons.provider') }: {{ appointmentBlock.provider.person.display }}</p>
                    <p>${ ui.message('appointmentschedulingui.appointmenttypes') }: <span ng-repeat="type in appointmentBlock.types"> {{ type.display }}{{ !\$last ? ', ' : '' }}</span> </p>
                    <p><a class="tooltip-link" ng-click="editAppointmentBlock(appointmentBlock.types)">${ ui.message('uicommons.edit')}</a>  <a class="tooltip-link" ng-click="showDeleteAppointmentBlockModal()">${ ui.message('uicommons.delete') }</a></p>
                </div>

            </div>
        </div>
    </div>

</div>
<%
    ui.decorateWith("appui", "standardEmrPage", [title: "Daily Appointments"])
    ui.includeCss("appointmentapp", "radiology.css")
    ui.includeJavascript("uicommons", "moment.js")
    ui.includeJavascript("appointmentapp", "jquery.form.js")
    ui.includeJavascript("appointmentapp", "jq.browser.select.js")

    ui.includeCss("appointmentapp", "scheduleAppointment.css")

    def angularLocale = context.locale.toString().toLowerCase();

    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("uicommons", "i18n/angular-locale_" + angularLocale + ".js")
    ui.includeJavascript("uicommons", "angular-ui/ui-bootstrap-tpls-0.13.0.min.js")
    ui.includeJavascript("uicommons", "angular-ui/ng-grid-2.0.7.min.js")
    ui.includeJavascript("uicommons", "angular-ui/ng-grid-locale_ht-custom.js")
    ui.includeJavascript("uicommons", "angular-resource.min.js")
    ui.includeJavascript("uicommons", "moment-with-locales.min.js")
    ui.includeJavascript("uicommons", "emr.js")

    ui.includeJavascript("uicommons", "angular-common.js")
    ui.includeJavascript("uicommons", "services/locationService.js")
    ui.includeJavascript("uicommons", "rest/restUtils.js")

    ui.includeCss("uicommons", "angular-ui/ng-grid.min.css")

    ui.includeJavascript("appointmentapp", "app.js")
    ui.includeJavascript("appointmentapp", "resources/appointmentResources.js")
    ui.includeJavascript("appointmentapp", "services/appointmentService.js")
    ui.includeJavascript("appointmentapp", "factories/ngGridHelper.js")
    ui.includeJavascript("appointmentapp", "controllers/dailyAppointmentsController.js")
    ui.includeJavascript("appointmentapp", "controllers/selectMultipleAppointmentTypesController.js")
    ui.includeJavascript("appointmentapp", "directives/selectMultipleAppointmentTypesDirective.js")
    ui.includeJavascript("appointmentapp", "factories/dailyAppointmentsHelper.js")
    ui.includeCss("appointmentapp", "dailyAppointments.css")
    ui.includeCss("appointmentapp", "selectMultipleAppointmentTypes.css")
%>

<%= ui.includeFragment("appui", "messages", [ codes: [
        'appointmentschedulingui.dailyScheduledAppointments.timeBlock',
        'appointmentschedulingui.dailyScheduledAppointments.provider',
        'appointmentschedulingui.dailyScheduledAppointments.patientName',
        'appointmentschedulingui.dailyScheduledAppointments.patientId',
        'appointmentschedulingui.dailyScheduledAppointments.appointmentType',
        'appointmentschedulingui.dailyScheduledAppointments.allProviders',
        'appointmentschedulingui.dailyScheduledAppointments.appointmentStatus',
        'appointmentschedulingui.dailyScheduledAppointments.allAppointmentStatuses',
        'appointmentschedulingui.dailyScheduledAppointments.noScheduledAppointments',
        'appointmentschedulingui.dailyScheduledAppointments.allAppointmentBlocks',
        'appointmentschedulingui.dailyScheduledAppointments.allServiceTypes',
        'appointmentscheduling.AppointmentBlock.error.scheduledAppointmentBlocks',
        'appointmentschedulingui.dailyScheduledAppointments.phoneNumber',
        'appointmentschedulingui.scheduleAppointment.status.type.active',
        'appointmentschedulingui.scheduleAppointment.status.type.scheduled',
        'appointmentschedulingui.scheduleAppointment.status.type.cancelled',
        'appointmentschedulingui.scheduleAppointment.status.type.missed',
        'appointmentschedulingui.scheduleAppointment.status.type.completed'
].flatten()
]) %>


<script type="text/javascript">
    var supportsAppointmentsTagUuid = '${ supportsAppointmentsTagUuid }';
    var sessionLocationUuid = '${ sessionLocationUuid }' ;
    var telephoneAttributeTypeName = '${ telephoneAttributeTypeName}';

    var jsLocale = '${ angularLocale }';  // used by the ngGrid widget
    if(jsLocale.indexOf('_') > -1){
        jsLocale = jsLocale.substring(0, jsLocale.indexOf('_'));
    }
</script>

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

<div class="clear"></div>
<%= ui.includeFragment("appointmentapp", "timeZoneWarning") %>
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
                    ${ ui.message("appointmentschedulingui.dailyScheduledAppointments.title") }
                </li>
            </ul>
        </div>
    </div>

    <div class="patient-header new-patient-header">
        <div class="show-icon">
            &nbsp;
        </div>

        <div>
            <div class="container" ng-app="appointmentscheduling.dailyAppointments"  ng-controller="DailyAppointmentsController" ng-cloak>
                <h1>${ ui.message("appointmentschedulingui.dailyScheduledAppointments.title") }</h1>
                <div id="filter-date" class="inline-box">
                    <p>${ ui.message("appointmentschedulingui.scheduleAppointment.date")}</p>
                    <span class="angular-datepicker" >
                        <input type="text" is-open="datePicker.opened" ng-model="filterDate" show-weeks="false" datepicker-popup="dd-MMMM-yyyy" readonly/>
                        <i class="icon-calendar small add-on" ng-click="datePicker.open(\$event)" ></i>
                    </span>
                </div>

                <div id="filter-location" class="inline-box">
                    <p>${ ui.message("uicommons.location") }</p>
                    <select ng-model="locationFilter" ng-options="l.display for l in locations">
                    </select>
                </div>
                <div class="appointment-filter">

                    <div id="filter-provider" class="inline-box">
                        <p>${ ui.message("uicommons.provider") }</p>
                        <select ng-model="providerFilter" ng-options="provider for provider in providers" ng-change="newSelectedProvider(providerFilter)">
                        </select>
                    </div>
                    <div id="filter-appointmentStatusType" class="inline-box">
                        <p>${ ui.message("appointmentschedulingui.dailyScheduledAppointments.appointmentStatus") }</p>
                        <select ng-model="appointmentStatusTypeFilter" ng-options="appointmentStatusType.value as appointmentStatusType.localizedDisplayName for appointmentStatusType in appointmentStatusTypes" ng-change="newSelectedAppointmentStatusType(appointmentStatusTypeFilter)">
                        </select>
                    </div>

                    <selectmultipleappointmenttypes
                            headermessage='${ ui.message("appointmentschedulingui.scheduleAppointment.serviceTypes") }'
                            viewall='${ ui.message("appointmentschedulingui.scheduleAppointment.viewAllTypes") }'
                            closemessage='${ ui.message("uicommons.close")}'
                            senderid = 'viewAppointmentBlock'
                            placeholdermessage = '${ ui.message("appointmentschedulingui.scheduleProviders.selectMultiplePlaceholder") }'
                            class="inline-box">
                    </selectmultipleappointmenttypes>
                </div>
                <div id="noDailyAppointments" ng-show="showNoDailyAppointments" class="inline-box">${ ui.message("appointmentschedulingui.dailyScheduledAppointments.noScheduledAppointments") }</div>
                <div id="loadingMessage" ng-show="showLoadingMessage">${ ui.message("appointmentschedulingui.dailyScheduledAppointments.loading") }</div>
                <div class="gridStyle" ng-grid="dailyAppointmentsGrid" id="dailyAppointmentsGrid"></div>
            </div>
        </div>
    </div>

</div>
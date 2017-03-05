<%
    ui.decorateWith("appui", "standardEmrPage", [title: "Manage Appointment Types"])
    ui.includeCss("appointmentapp", "radiology.css")
    ui.includeCss("appointmentapp", "createAppointmentStyle.css")
    ui.includeCss("appointmentapp", "appointmentTypeList_jQueryDatatable.css")
    ui.includeJavascript("uicommons", "moment.js")
    ui.includeJavascript("appointmentapp", "jquery.form.js")
    ui.includeJavascript("appointmentapp", "jq.browser.select.js")
    ui.includeJavascript("appointmentapp", "jquery.dataTables.js")
%>

<script type="text/javascript">
    jq(function () {
        var resultMessage = "${resultMessage}";
        if (resultMessage != "") {
            emr.successMessage(resultMessage);
        }
        jq('#appointmentTypesTable')
                .dataTable(
                        {
                            "aoColumns": [{
                                "bSortable": true
                            }, {
                                "bSortable": false
                            }, {
                                "bSortable": true
                            }],
                            "aLengthMenu": [
                                [5, 10, 25, 50, -1],
                                [5, 10, 25, 50, "All"]],
                            "iDisplayLength": 5,
                            "sDom": "<'fg-toolbar ui-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix'l<'addons'>>t<'fg-toolbar ui-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix'ipT<'toolbar'>>",
                            "bLengthChange": true,
                            "bFilter": false,
                            "bInfo": true,
                            "bPaginate": true,
                            "bJQueryUI": true,

                            "fnDrawCallback": function () {
                                jq(".addons").html("");
                                jq(".addons").prepend(
                                        "<% if (context.hasPrivilege("Manage Appointment Types")) { %> <input type='button' value='${ ui.message('appointmentscheduling.AppointmentType.add') }' class='saveButton' style='margin:10px; float:right; display:block;' onclick='addNewAppointmentType()'/><% } %>");
                            },
                        });
    });

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
                    Manage Appointment Types
                </li>
            </ul>
        </div>
    </div>

    <div class="patient-header new-patient-header">
        <div class="demographics">
            <h1 class="name" style="border-bottom: 1px solid #ddd;">
                <span>&nbsp;MANAGE SERVICE TYPES &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>
            </h1>
        </div>

        <div class="show-icon">
            &nbsp;
        </div>


        <form method="post" class="box">
            <table id="appointmentTypesTable">
                <thead>
                <tr>
                    <th>${ui.message("general.name")}</th>
                    <th>${ui.message("general.description")}</th>
                    <th>${ui.message("appointmentapp.AppointmentType.duration")}</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </form>
    </div>

</div>
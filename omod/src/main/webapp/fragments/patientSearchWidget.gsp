<%
    config.require("afterSelectedUrl")
    def breadcrumbOverride = config.breadcrumbOverride ?: ""

    ui.includeCss("uicommons", "datatables/dataTables_jui.css")
    ui.includeCss("appointmentapp", "patientsearch/patientSearchWidget.css")
    ui.includeJavascript("uicommons", "datatables/jquery.dataTables.min.js")
    ui.includeJavascript("appointmentapp", "patientsearch/patientSearchWidget.js")
//    ui.includeJavascript("uicommons", "moment-with-locales.min.js")
%>
<script type="text/javascript">

    var lastViewedPatients = [];
    function handlePatientRowSelection() {
        this.handle = function (row) {
            var uuid = row.uuid;
            location.href = '/' + OPENMRS_CONTEXT_PATH + emr.applyContextModel('${ ui.escapeJs(config.afterSelectedUrl) }', { patientId: uuid,appointmentBlock: '${ ui.escapeJs(config.afterSelectedUrl) }', breadcrumbOverride: '${ ui.escapeJs(breadcrumbOverride) }'});
        }
    }
    var handlePatientRowSelection =  new handlePatientRowSelection();
    jq(function() {
        var widgetConfig = {
            initialPatients: lastViewedPatients,
            doInitialSearch: ${ doInitialSearch ? "\"" + ui.escapeJs(doInitialSearch) + "\"" : "null" },
            minSearchCharacters: ${ config.minSearchCharacters ?: 3 },
            afterSelectedUrl: '${ ui.escapeJs(config.afterSelectedUrl) }',
            breadcrumbOverride: '${ ui.escapeJs(breadcrumbOverride) }',
            searchDelayShort: ${ searchDelayShort },
            searchDelayLong: ${ searchDelayLong },
            handleRowSelection: ${ config.rowSelectionHandler ?: "handlePatientRowSelection" },
            dateFormat: '${ dateFormatJS }',
            locale: '${ locale }',
            defaultLocale: '${ defaultLocale }',
            messages: {
                info: '${ ui.message("coreapps.search.info") }',
                first: '${ ui.message("coreapps.search.first") }',
                previous: '${ ui.message("coreapps.search.previous") }',
                next: '${ ui.message("coreapps.search.next") }',
                last: '${ ui.message("coreapps.search.last") }',
                noMatchesFound: '${ ui.message("coreapps.search.noMatchesFound") }',
                noData: '${ ui.message("coreapps.search.noData") }',
                recent: '${ ui.message("coreapps.search.label.recent") }',
                searchError: '${ ui.message("coreapps.search.error") }',
                identifierColHeader: '${ ui.message("coreapps.search.identifier") }',
                nameColHeader: '${ ui.message("coreapps.search.name") }',
                genderColHeader: '${ ui.message("coreapps.gender") }',
                ageColHeader: '${ ui.message("coreapps.age") }',
                birthdateColHeader: '${ ui.message("coreapps.birthdate") }'
            }
        };

        new PatientSearchWidget(widgetConfig);
    });
</script>

<form method="get" id="patient-search-form" onsubmit="return false">
    <input type="text" id="patient-search" placeholder="${ ui.message("coreapps.findPatient.search.placeholder") }" autocomplete="off" <% if (doInitialSearch) { %>value="${doInitialSearch}"<% } %>/><i id="patient-search-clear-button" class="small icon-remove-sign"></i>
</form>

<div id="patient-search-results"></div>

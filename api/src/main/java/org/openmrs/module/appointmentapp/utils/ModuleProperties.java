package org.openmrs.module.appointmentapp.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by mstan on 17/03/2017.
 */
public abstract class ModuleProperties {
    private static final Log log = LogFactory.getLog(ModuleProperties.class);
    @Autowired
    @Qualifier("conceptService")
    protected ConceptService conceptService;
    @Autowired
    @Qualifier("encounterService")
    protected EncounterService encounterService;
    @Autowired
    @Qualifier("visitService")
    protected VisitService visitService;
    @Autowired
    @Qualifier("orderService")
    protected OrderService orderService;
    @Autowired
    @Qualifier("adminService")
    protected AdministrationService administrationService;
    @Autowired
    @Qualifier("locationService")
    protected LocationService locationService;
    @Autowired
    @Qualifier("userService")
    protected UserService userService;
    @Autowired
    @Qualifier("patientService")
    protected PatientService patientService;
    @Autowired
    @Qualifier("personService")
    protected PersonService personService;
    @Autowired
    @Qualifier("providerService")
    protected ProviderService providerService;
    @Autowired
    @Qualifier("formService")
    protected FormService formService;

    public ModuleProperties() {
    }

    public void setConceptService(ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    public void setAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    public void setEncounterService(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setProviderService(ProviderService providerService) {
        this.providerService = providerService;
    }

    protected ConceptClass getConceptClassByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.getGlobalProperty(globalPropertyName, true);
        ConceptClass conceptClass = this.conceptService.getConceptClassByUuid(globalProperty);
        if(conceptClass == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return conceptClass;
        }
    }

    protected Concept getConceptByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        Concept concept = this.conceptService.getConceptByUuid(globalProperty);
        if(concept == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return concept;
        }
    }

    protected Concept getSingleConceptByMapping(ConceptSource conceptSource, String code) {
        List candidates = this.conceptService.getConceptsByMapping(code, conceptSource.getName(), false);
        if(candidates.size() == 0) {
            throw new IllegalStateException("Configuration required: can\'t find a concept by mapping " + conceptSource.getName() + ":" + code);
        } else if(candidates.size() == 1) {
            return (Concept)candidates.get(0);
        } else {
            throw new IllegalStateException("Configuration required: found more than one concept mapped as " + conceptSource.getName() + ":" + code);
        }
    }

    protected ConceptSource getConceptSourceByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        ConceptSource conceptSource = this.conceptService.getConceptSourceByUuid(globalProperty);
        if(conceptSource == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return conceptSource;
        }
    }

    protected EncounterType getEncounterTypeByUuid(String uuid, boolean required) {
        EncounterType encounterType = this.encounterService.getEncounterTypeByUuid(uuid);
        if(required && encounterType == null) {
            throw new IllegalStateException("Cannot find required EncounterType with uuid = " + uuid);
        } else {
            return encounterType;
        }
    }

    protected EncounterType getEncounterTypeByGlobalProperty(String globalPropertyName) {
        return this.getEncounterTypeByGlobalProperty(globalPropertyName, true);
    }

    protected EncounterType getEncounterTypeByGlobalProperty(String globalPropertyName, boolean required) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        EncounterType encounterType = this.encounterService.getEncounterTypeByUuid(globalProperty);
        if(required && encounterType == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return encounterType;
        }
    }

    protected EncounterRole getEncounterRoleByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        EncounterRole encounterRole = this.encounterService.getEncounterRoleByUuid(globalProperty);
        if(encounterRole == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return encounterRole;
        }
    }

    protected VisitType getVisitTypeByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        VisitType visitType = this.visitService.getVisitTypeByUuid(globalProperty);
        if(visitType == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return visitType;
        }
    }

    protected VisitType getVisitTypeByUuid(String uuid, boolean required) {
        VisitType visitType = this.visitService.getVisitTypeByUuid(uuid);
        if(required && visitType == null) {
            throw new IllegalStateException("Cannot find required VisitType with uuid = " + uuid);
        } else {
            return visitType;
        }
    }

    protected OrderType getOrderTypeByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        OrderType orderType = this.orderService.getOrderTypeByUuid(globalProperty);
        if(orderType == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return orderType;
        }
    }

    protected Location getLocationByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        Location location = this.locationService.getLocationByUuid(globalProperty);
        if(location == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return location;
        }
    }

    protected Provider getProviderByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        Provider provider = this.providerService.getProviderByUuid(globalProperty);
        if(provider == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return provider;
        }
    }

    protected Form getFormByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        return this.formService.getFormByUuid(globalProperty);
    }

    protected PatientIdentifierType getPatientIdentifierTypeByGlobalProperty(String globalPropertyName, boolean required) {
        String globalProperty = this.getGlobalProperty(globalPropertyName, required);
        PatientIdentifierType patientIdentifierType = GeneralUtils.getPatientIdentifierType(globalProperty, this.patientService);
        if(required && patientIdentifierType == null) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return patientIdentifierType;
        }
    }

    protected List<PatientIdentifierType> getPatientIdentifierTypesByGlobalProperty(String globalPropertyName, boolean required) {
        ArrayList types = new ArrayList();
        String globalProperty = this.getGlobalProperty(globalPropertyName, required);
        if(StringUtils.isNotEmpty(globalProperty)) {
            String[] arr$ = globalProperty.split(",");
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String type = arr$[i$];
                PatientIdentifierType patientIdentifierType = this.patientService.getPatientIdentifierTypeByUuid(type);
                if(patientIdentifierType != null) {
                    types.add(patientIdentifierType);
                } else {
                    log.warn("Global property " + globalPropertyName + " specifies an unknown patient identifier type: " + type);
                }
            }
        }

        return types;
    }

    protected Integer getIntegerByGlobalProperty(String globalPropertyName) {
        String globalProperty = this.getGlobalProperty(globalPropertyName, true);

        try {
            return Integer.valueOf(globalProperty);
        } catch (Exception var4) {
            throw new IllegalStateException("Global property " + globalPropertyName + " value of " + globalProperty + " is not parsable as an Integer");
        }
    }

    protected String getGlobalProperty(String globalPropertyName, boolean required) {
        String globalProperty = this.administrationService.getGlobalProperty(globalPropertyName);
        if(required && StringUtils.isEmpty(globalProperty)) {
            throw new IllegalStateException("Configuration required: " + globalPropertyName);
        } else {
            return globalProperty;
        }
    }

    protected Collection<Concept> getConceptsByGlobalProperty(String gpName) {
        String gpValue = this.getGlobalProperty(gpName, false);
        if(!org.springframework.util.StringUtils.hasText(gpValue)) {
            return Collections.emptyList();
        } else {
            ArrayList result = new ArrayList();
            String[] concepts = gpValue.split("\\,");
            String[] arr$ = concepts;
            int len$ = concepts.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String concept = arr$[i$];
                Concept foundConcept = this.conceptService.getConceptByUuid(concept);
                if(foundConcept == null) {
                    String[] mapping = concept.split("\\:");
                    if(mapping.length == 2) {
                        foundConcept = this.conceptService.getConceptByMapping(mapping[0], mapping[1]);
                    }
                }

                if(foundConcept == null) {
                    throw new IllegalStateException("Invalid configuration: concept \'" + concept + "\' defined in " + gpName + " does not exist");
                }

                result.add(foundConcept);
            }

            return result;
        }
    }
}


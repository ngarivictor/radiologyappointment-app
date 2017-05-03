package org.openmrs.module.appointmentapp.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.APIException;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.util.LocaleUtility;
import org.openmrs.util.OpenmrsUtil;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by mstan on 17/03/2017.
 */
public class GeneralUtils {
    public GeneralUtils() {
    }

    public static Locale getDefaultLocale(User user) {
        if(user != null && user.getUserProperties() != null && user.getUserProperties().containsKey("defaultLocale")) {
            String localeString = user.getUserProperty("defaultLocale");
            Locale locale = normalizeLocale(localeString);
            return locale;
        } else {
            return null;
        }
    }

    public static Locale normalizeLocale(String localeString) {
        if(localeString == null) {
            return null;
        } else {
            localeString = localeString.trim();
            if(localeString.isEmpty()) {
                return null;
            } else {
                int len = localeString.length();

                for(int locale = 0; locale < len; ++locale) {
                    char c = localeString.charAt(locale);
                    if((c <= 32 || c >= 127 || (c >= 32 || c <= 127) && !Character.isLetter(c) && c != 95) && c != 9) {
                        localeString = localeString.replaceFirst(Character.valueOf(c).toString(), "");
                        --len;
                        --locale;
                    }
                }

                Locale var4 = LocaleUtility.fromSpecification(localeString);
                if(LocaleUtility.isValid(var4)) {
                    return var4;
                } else {
                    return null;
                }
            }
        }
    }

    public static PatientIdentifierType getPatientIdentifierType(String id) {
        return getPatientIdentifierType(id, Context.getPatientService());
    }

    public static PatientIdentifierType getPatientIdentifierType(String id, PatientService patientService) {
        PatientIdentifierType identifierType = null;
        if(id != null) {
            try {
                int ex = Integer.parseInt(id);
                identifierType = patientService.getPatientIdentifierType(Integer.valueOf(ex));
                if(identifierType != null) {
                    return identifierType;
                }
            } catch (Exception var4) {
                ;
            }

            if(isValidUuidFormat(id)) {
                identifierType = patientService.getPatientIdentifierTypeByUuid(id);
                if(identifierType != null) {
                    return identifierType;
                }
            } else {
                identifierType = patientService.getPatientIdentifierTypeByName(id);
            }
        }

        return identifierType;
    }

    public static boolean isValidUuidFormat(String uuid) {
        return uuid.length() >= 36 && uuid.length() <= 38 && !uuid.contains(" ");
    }

    public static String getPersonAddressProperty(PersonAddress address, String property) {
        try {
            Class e = Context.loadClass("org.openmrs.PersonAddress");
            Method getPersonAddressProperty = e.getMethod("get" + property.substring(0, 1).toUpperCase() + property.substring(1), new Class[0]);
            return (String)getPersonAddressProperty.invoke(address, new Object[0]);
        } catch (Exception var4) {
            throw new APIException("Invalid property name " + property + " passed to getPersonAddressProperty");
        }
    }

    public static PatientIdentifier getPatientIdentifier(Patient patient, PatientIdentifierType patientIdentifierType, Location location) {
        List patientIdentifiers = patient.getPatientIdentifiers(patientIdentifierType);
        if(patientIdentifiers != null && patientIdentifiers.size() != 0) {
            Iterator i$ = patientIdentifiers.iterator();

            PatientIdentifier patientIdentifier;
            do {
                if(!i$.hasNext()) {
                    i$ = patientIdentifiers.iterator();

                    do {
                        if(!i$.hasNext()) {
                            return null;
                        }

                        patientIdentifier = (PatientIdentifier)i$.next();
                    } while(!location.equals(patientIdentifier.getLocation()));

                    return patientIdentifier;
                }

                patientIdentifier = (PatientIdentifier)i$.next();
            } while(!location.equals(patientIdentifier.getLocation()) || !patientIdentifier.isPreferred().booleanValue());

            return patientIdentifier;
        } else {
            return null;
        }
    }

    public static boolean setPropertyIfDifferent(Object bean, String propertyName, Object newValue) {
        try {
            Object ex = PropertyUtils.getProperty(bean, propertyName);
            if(OpenmrsUtil.nullSafeEquals(ex, newValue)) {
                return false;
            } else {
                PropertyUtils.setProperty(bean, propertyName, newValue);
                return true;
            }
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static List<Patient> getLastViewedPatients(User user) {
        ArrayList lastViewed = new ArrayList();
        if(user != null) {
            user = Context.getUserService().getUser(user.getId());
            String lastViewedPatientIdsString = user.getUserProperty("emrapi.lastViewedPatientIds");
            if(StringUtils.isNotBlank(lastViewedPatientIdsString)) {
                PatientService ps = Context.getPatientService();
                lastViewedPatientIdsString = lastViewedPatientIdsString.replaceAll("\\s", "");
                String[] patientIds = lastViewedPatientIdsString.split(",");
                String[] arr$ = patientIds;
                int len$ = patientIds.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String pId = arr$[i$];

                    try {
                        Patient e = ps.getPatient(Integer.valueOf(pId));
                        if(e != null && !e.isVoided().booleanValue() && !e.isPersonVoided().booleanValue()) {
                            lastViewed.add(e);
                        }
                    } catch (NumberFormatException var10) {
                        ;
                    }
                }
            }
        }

        Collections.reverse(lastViewed);
        return lastViewed;
    }

    public static Date getCurrentDateIfNull(Date date) {
        return date == null?new Date():date;
    }
}


package org.openmrs.module.appointmentapp;

import org.openmrs.Location;
import org.openmrs.PatientIdentifierType;
import org.openmrs.User;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mstan on 23/03/2017.
 */
@Transactional
public interface IdentifierSourceService extends OpenmrsService {
    @Transactional(
            readOnly = true
    )
    List<Class<? extends IdentifierSource>> getIdentifierSourceTypes();

    @Transactional(
            readOnly = true
    )
    IdentifierSource getIdentifierSource(Integer var1) throws APIException;

    @Transactional(
            readOnly = true
    )
    List<IdentifierSource> getAllIdentifierSources(boolean var1) throws APIException;

    @Transactional(
            readOnly = true
    )
    Map<PatientIdentifierType, List<IdentifierSource>> getIdentifierSourcesByType(boolean var1) throws APIException;

    @Transactional
    @Authorized({"Manage Identifier Sources"})
    IdentifierSource saveIdentifierSource(IdentifierSource var1) throws APIException;

    @Transactional
    @Authorized({"Manage Identifier Sources"})
    void purgeIdentifierSource(IdentifierSource var1) throws APIException;

    @Transactional
    @Authorized({"Edit Patient Identifiers"})
    String generateIdentifier(PatientIdentifierType var1, String var2);

    @Transactional
    @Authorized({"Edit Patient Identifiers"})
    String generateIdentifier(PatientIdentifierType var1, Location var2, String var3);

    @Transactional
    @Authorized({"Edit Patient Identifiers"})
    String generateIdentifier(IdentifierSource var1, String var2) throws APIException;

    @Transactional
    @Authorized({"Generate Batch of Identifiers"})
    List<String> generateIdentifiers(IdentifierSource var1, Integer var2, String var3) throws APIException;

    @Transactional(
            readOnly = true
    )
    IdentifierSourceProcessor getProcessor(IdentifierSource var1);

    @Transactional(
            readOnly = true
    )
    void registerProcessor(Class<? extends IdentifierSource> var1, IdentifierSourceProcessor var2) throws APIException;

    @Transactional(
            readOnly = true
    )
    List<PooledIdentifier> getAvailableIdentifiers(IdentifierPool var1, int var2) throws APIException;

    @Transactional(
            readOnly = true
    )
    int getQuantityInPool(IdentifierPool var1, boolean var2, boolean var3) throws APIException;

    @Transactional
    @Authorized({"Upload Batch of Identifiers"})
    void addIdentifiersToPool(IdentifierPool var1, List<String> var2) throws APIException;

    @Transactional
    @Authorized({"Upload Batch of Identifiers"})
    void addIdentifiersToPool(IdentifierPool var1, Integer var2) throws APIException;

    @Transactional(
            readOnly = true
    )
    AutoGenerationOption getAutoGenerationOption(Integer var1) throws APIException;

    @Transactional(
            readOnly = true
    )
    AutoGenerationOption getAutoGenerationOption(PatientIdentifierType var1, Location var2) throws APIException;

    @Transactional(
            readOnly = true
    )
    List<AutoGenerationOption> getAutoGenerationOptions(PatientIdentifierType var1) throws APIException;

    @Transactional(
            readOnly = true
    )
    AutoGenerationOption getAutoGenerationOption(PatientIdentifierType var1) throws APIException;

    @Transactional
    @Authorized({"Manage Auto Generation Options"})
    AutoGenerationOption saveAutoGenerationOption(AutoGenerationOption var1) throws APIException;

    @Transactional
    @Authorized({"Manage Auto Generation Options"})
    void purgeAutoGenerationOption(AutoGenerationOption var1) throws APIException;

    @Transactional(
            readOnly = true
    )
    List<LogEntry> getLogEntries(IdentifierSource var1, Date var2, Date var3, String var4, User var5, String var6) throws APIException;

    @Transactional
    void checkAndRefillIdentifierPool(IdentifierPool var1);

    @Transactional(
            readOnly = true
    )
    List<PatientIdentifierType> getPatientIdentifierTypesByAutoGenerationOption(Boolean var1, Boolean var2);

    @Transactional(
            readOnly = true
    )
    IdentifierSource getIdentifierSourceByUuid(String var1);

    void saveSequenceValue(SequentialIdentifierGenerator var1, long var2);

    Long getSequenceValue(SequentialIdentifierGenerator var1);

    List<String> generateIdentifiersInternal(Integer var1, Integer var2, String var3);
}

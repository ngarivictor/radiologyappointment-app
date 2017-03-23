package org.openmrs.module.appointmentapp;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mstan on 23/03/2017.
 */
public interface DefinitionService<T extends Definition> extends OpenmrsService {
    Class<T> getDefinitionType();

    List<Class<? extends T>> getDefinitionTypes();

    @Transactional(
            readOnly = true
    )
    <D extends T> D getDefinition(Class<D> var1, Integer var2) throws APIException;

    @Transactional(
            readOnly = true
    )
    T getDefinitionByUuid(String var1) throws APIException;

    @Transactional(
            readOnly = true
    )
    T getDefinition(String var1, Class<? extends T> var2);

    @Transactional(
            readOnly = true
    )
    List<T> getAllDefinitions(boolean var1);

    @Transactional(
            readOnly = true
    )
    List<DefinitionSummary> getAllDefinitionSummaries(boolean var1);

    @Transactional(
            readOnly = true
    )
    int getNumberOfDefinitions(boolean var1);

    @Transactional(
            readOnly = true
    )
    List<T> getDefinitions(String var1, boolean var2);

    @Transactional(
            readOnly = true
    )
    List<T> getDefinitionsByTag(String var1);

    @Transactional
    <D extends T> D saveDefinition(D var1) throws APIException;

    @Transactional
    void purgeDefinition(T var1);


}

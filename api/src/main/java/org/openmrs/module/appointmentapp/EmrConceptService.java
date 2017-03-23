package org.openmrs.module.appointmentapp;

import org.openmrs.*;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by mstan on 23/03/2017.
 */
public interface EmrConceptService {
    List<Concept> getConceptsSameOrNarrowerThan(ConceptReferenceTerm var1);

    Concept getConcept(String var1);

    List<ConceptSearchResult> conceptSearch(String var1, Locale var2, Collection<ConceptClass> var3, Collection<Concept> var4, Collection<ConceptSource> var5, Integer var6);
}

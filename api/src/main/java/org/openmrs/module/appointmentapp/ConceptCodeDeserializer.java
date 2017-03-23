package org.openmrs.module.appointmentapp;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.openmrs.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author Stanslaus Odhaimbo
 *         Created on 23/03/2017.
 */
public class ConceptCodeDeserializer extends JsonDeserializer<Concept> {
    private EmrConceptService emrConceptService = new EmrConceptService() {
        public List<Concept> getConceptsSameOrNarrowerThan(ConceptReferenceTerm term) {
            return null;
        }

        public Concept getConcept(String mappingOrUuid) {
            Concept concept = new Concept();
            concept.setUuid(mappingOrUuid);
            return concept;
        }

        public List<ConceptSearchResult> conceptSearch(String query, Locale locale, Collection<ConceptClass> classes, Collection<Concept> inSets, Collection<ConceptSource> sources, Integer limit) {
            return null;
        }
    };

    public ConceptCodeDeserializer() {
    }

    public Concept deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String conceptCode = jp.getText();
        Concept concept = this.emrConceptService.getConcept(conceptCode);
        if(concept == null) {
            throw ctxt.instantiationException(Concept.class, "No concept with code or uuid: " + conceptCode);
        } else {
            return concept;
        }
    }
}
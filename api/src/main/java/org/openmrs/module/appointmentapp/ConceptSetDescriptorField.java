package org.openmrs.module.appointmentapp;

/**
 * @author Stanslaus Odhaimbo
 *         Created on 23/03/2017.
 */
public class ConceptSetDescriptorField {
    private final String name;
    private final String conceptCode;
    private final boolean required;

    private ConceptSetDescriptorField(String name, String conceptCode, boolean required) {
        this.name = name;
        this.conceptCode = conceptCode;
        this.required = required;
    }

    public String getName() {
        return this.name;
    }

    public String getConceptCode() {
        return this.conceptCode;
    }

    public boolean isRequired() {
        return this.required;
    }

    public static ConceptSetDescriptorField required(String fieldName, String conceptCode) {
        return new ConceptSetDescriptorField(fieldName, conceptCode, true);
    }

    public static ConceptSetDescriptorField optional(String fieldName, String conceptCode) {
        return new ConceptSetDescriptorField(fieldName, conceptCode, false);
    }
}

package org.openmrs.module.appointmentapp;

import org.openmrs.OpenmrsMetadata;
import org.openmrs.api.db.SerializedObject;

/**
 * Created by mstan on 23/03/2017.
 */
public class DefinitionSummary {
    private String uuid;
    private String name;
    private String description;
    private String type;

    public DefinitionSummary() {
    }

    public DefinitionSummary(SerializedObject so) {
        this.uuid = so.getUuid();
        this.name = so.getName();
        this.description = so.getDescription();
        this.type = so.getSubtype();
    }

    public DefinitionSummary(OpenmrsMetadata metadata) {
        this.uuid = metadata.getUuid();
        this.name = metadata.getName();
        this.description = metadata.getDescription();
        this.type = metadata.getClass().getName();
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package org.openmrs.module.appointmentapp;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by mstan on 23/03/2017.
 */
public class Parameter implements Serializable {
    public static final long serialVersionUID = 1L;
    private String name;
    private String label;
    private Class<?> type;
    private Class<? extends Collection> collectionType;
    private Object defaultValue;
    private boolean required;
    private Properties widgetConfiguration;

    public Parameter() {
        this.required = true;
    }

    public Parameter(String name, String label, Class<?> type, Class<? extends Collection> collectionType, Object defaultValue, Properties widgetConfiguration) {
        this.required = true;
        this.setName(name);
        this.setLabel(label);
        this.setType(type);
        this.setCollectionType(collectionType);
        this.setDefaultValue(defaultValue);
        this.setWidgetConfiguration(widgetConfiguration);
    }

    public Parameter(String name, String label, Class<?> type, Class<? extends Collection> collectionType, Object defaultValue) {
        this(name, label, type, collectionType, defaultValue, (Properties)null);
    }

    public Parameter(String name, String label, Class<?> type) {
        this(name, label, type, (Class)null, (Object)null, (Properties)null);
    }

    public Parameter(String name, String label, Class<?> type, Properties widgetConfiguration) {
        this(name, label, type, (Class)null, (Object)null, widgetConfiguration);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter<name=" + this.name + ",label=" + this.label);
        if(this.collectionType != null) {
            sb.append(this.collectionType + "<");
        }

        sb.append(",type=" + (this.type == null?"null":this.type.getName()));
        if(this.collectionType != null) {
            sb.append(">");
        }

        sb.append(",defaultValue=" + this.defaultValue + ">");
        if(this.widgetConfiguration != null) {
            sb.append("widgetConfiguration=");
            sb.append(this.getWidgetConfigurationAsString());
        }

        return sb.toString();
    }

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Parameter) {
            Parameter p = (Parameter)obj;
            return StringUtils.equalsIgnoreCase(p.getName(), this.getName());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.name == null?0:31 * this.name.toUpperCase().hashCode();
    }

    public String getLabelOrName() {
        return StringUtils.isNotBlank(this.label)?this.label:this.name;
    }

    public String getExpression() {
        return "${" + this.getName() + "}";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Class<?> getType() {
        return this.type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Class<? extends Collection> getCollectionType() {
        return this.collectionType;
    }

    public void setCollectionType(Class<? extends Collection> collectionType) {
        this.collectionType = collectionType;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Properties getWidgetConfiguration() {
        return this.widgetConfiguration;
    }

    public void addToWidgetConfiguration(String propertyName, String propertyValue) {
        if(this.widgetConfiguration == null) {
            this.widgetConfiguration = new Properties();
        }

        this.widgetConfiguration.setProperty(propertyName, propertyValue);
    }

    public String getWidgetConfigurationAsString() {
        StringBuilder sb = new StringBuilder();
        if(this.widgetConfiguration != null) {
            int i = 0;

            Object o;
            for(Iterator i$ = this.widgetConfiguration.keySet().iterator(); i$.hasNext(); sb.append(o.toString() + "=" + this.widgetConfiguration.get(o))) {
                o = i$.next();
                if(i++ > 0) {
                    sb.append("|");
                }
            }
        }

        return sb.toString();
    }

    public void setWidgetConfiguration(Properties widgetConfiguration) {
        this.widgetConfiguration = widgetConfiguration;
    }
}

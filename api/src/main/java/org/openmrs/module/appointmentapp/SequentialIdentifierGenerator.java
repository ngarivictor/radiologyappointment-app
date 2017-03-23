package org.openmrs.module.appointmentapp;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.patient.IdentifierValidator;

/**
 * Created by mstan on 23/03/2017.
 */
public class SequentialIdentifierGenerator extends BaseIdentifierSource {
    private Long nextSequenceValue;
    private String prefix;
    private String suffix;
    private String firstIdentifierBase;
    private Integer minLength;
    private Integer maxLength;
    private String baseCharacterSet;

    public SequentialIdentifierGenerator() {
    }

    public boolean isInitialized() {
        Long nextSequenceValue = ((IdentifierSourceService) Context.getService(IdentifierSourceService.class)).getSequenceValue(this);
        return nextSequenceValue != null && nextSequenceValue.longValue() > 0L;
    }

    public String getIdentifierForSeed(long seed) {
        int seqLength = this.firstIdentifierBase == null?1:this.firstIdentifierBase.length();
        String identifier = IdgenUtil.convertToBase(seed, this.baseCharacterSet.toCharArray(), seqLength);
        identifier = this.prefix == null?identifier:this.prefix + identifier;
        identifier = this.suffix == null?identifier:identifier + this.suffix;
        if(this.getIdentifierType() != null && StringUtils.isNotEmpty(this.getIdentifierType().getValidator())) {
            try {
                Class e = Context.loadClass(this.getIdentifierType().getValidator());
                IdentifierValidator v = (IdentifierValidator)e.newInstance();
                identifier = v.getValidIdentifier(identifier);
            } catch (Exception var7) {
                throw new RuntimeException("Error generating check digit with " + this.getIdentifierType().getValidator(), var7);
            }
        }

        if(this.minLength != null && this.minLength.intValue() > 0 && identifier.length() < this.minLength.intValue()) {
            throw new RuntimeException("Invalid configuration for IdentifierSource. Length minimum set to " + this.minLength + " but generated " + identifier);
        } else if(this.maxLength != null && this.maxLength.intValue() > 0 && identifier.length() > this.maxLength.intValue()) {
            throw new RuntimeException("Invalid configuration for IdentifierSource. Length maximum set to " + this.maxLength + " but generated " + identifier);
        } else {
            return identifier;
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFirstIdentifierBase() {
        return this.firstIdentifierBase;
    }

    public void setFirstIdentifierBase(String firstIdentifierBase) {
        this.firstIdentifierBase = firstIdentifierBase;
    }

    public Integer getMinLength() {
        return this.minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getBaseCharacterSet() {
        return this.baseCharacterSet;
    }

    public void setBaseCharacterSet(String baseCharacterSet) {
        this.baseCharacterSet = baseCharacterSet;
    }
}
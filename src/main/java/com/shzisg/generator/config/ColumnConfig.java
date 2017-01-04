package com.shzisg.generator.config;

public class ColumnConfig {
    private String name;
    private String alias = null;
    private String type;
    private boolean useWrapper = false;
    private String wrapperType;
    private Boolean ignore;
    private Boolean nullable;
    private boolean enumAsString = true;
    private Boolean isPrimary;
    private String comment;
    private boolean isExtend;

    public ColumnConfig() {

    }

    public ColumnConfig(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (alias == null) {
            this.alias = name;
        }
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUseWrapper() {
        return useWrapper;
    }

    public void setUseWrapper(boolean useWrapper) {
        this.useWrapper = useWrapper;
    }

    public String getWrapperType() {
        return wrapperType;
    }

    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    public Boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

    public Boolean isNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isEnumAsString() {
        return enumAsString;
    }

    public void setEnumAsString(boolean enumAsString) {
        this.enumAsString = enumAsString;
    }

    public Boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isExtend() {
        return isExtend;
    }

    public void setExtend(boolean extend) {
        isExtend = extend;
    }
}

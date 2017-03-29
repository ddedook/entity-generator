package com.shzisg.generator.config;

import java.util.List;

public class TableConfig {
    private String name;
    private String className;
    private String comment;
    private String suffix;
    private List<ColumnConfig> columns;
    private List<ColumnConfig> extend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<ColumnConfig> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }

    public List<ColumnConfig> getExtend() {
        return extend;
    }

    public void setExtend(List<ColumnConfig> extend) {
        this.extend = extend;
    }
}

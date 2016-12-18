package com.shzisg.generator.output;

import com.shzisg.generator.config.ColumnConfig;

import java.util.ArrayList;
import java.util.List;

public class PropertyDefine {
    private String type;
    private String name;
    private List<String> decorators = new ArrayList<>();
    private ColumnConfig columnConfig;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDecorators() {
        return decorators;
    }

    public void setDecorators(List<String> decorators) {
        this.decorators = decorators;
    }

    public PropertyDefine addDecorator(String decorator) {
        this.decorators.add(decorator);
        return this;
    }

    public ColumnConfig getColumnConfig() {
        return columnConfig;
    }

    public void setColumnConfig(ColumnConfig columnConfig) {
        this.columnConfig = columnConfig;
    }
}

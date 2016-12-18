package com.shzisg.generator.output;

import java.util.List;

public class DomainDefine {
    private boolean swagger = false;
    private boolean lombok = false;
    private boolean jackson = false;
    private String rootPath;
    private List<EntityDefine> entities;

    public boolean isSwagger() {
        return swagger;
    }

    public void setSwagger(boolean swagger) {
        this.swagger = swagger;
    }

    public boolean isLombok() {
        return lombok;
    }

    public void setLombok(boolean lombok) {
        this.lombok = lombok;
    }

    public boolean isJackson() {
        return jackson;
    }

    public void setJackson(boolean jackson) {
        this.jackson = jackson;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public List<EntityDefine> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityDefine> entities) {
        this.entities = entities;
    }
}

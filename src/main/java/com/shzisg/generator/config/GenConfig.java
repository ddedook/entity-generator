package com.shzisg.generator.config;

import java.util.List;

public class GenConfig {
    private boolean swagger = false;
    private boolean lombok = false;
    private boolean jackson = false;
    private List<DomainConfig> domains;

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

    public List<DomainConfig> getDomains() {
        return domains;
    }

    public void setDomains(List<DomainConfig> domains) {
        this.domains = domains;
    }
}

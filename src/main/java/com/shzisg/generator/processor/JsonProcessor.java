package com.shzisg.generator.processor;

import com.shzisg.generator.config.ColumnConfig;
import com.shzisg.generator.output.DomainDefine;
import com.shzisg.generator.output.EntityDefine;
import com.shzisg.generator.output.PropertyDefine;

public class JsonProcessor implements Processor {

    @Override
    public DomainDefine process(DomainDefine domainDefine) {
        if (!domainDefine.isJackson()) {
            return domainDefine;
        }
        for (EntityDefine define : domainDefine.getEntities()) {
            for (PropertyDefine prop : define.getProperties()) {
                ColumnConfig config = prop.getColumnConfig();
                if (config.isIgnore()) {
                    define.addImport("com.fasterxml.jackson.annotation.JsonIgnore");
                    prop.addDecorator("@JsonIgnore");
                }
            }
        }
        return domainDefine;
    }
}

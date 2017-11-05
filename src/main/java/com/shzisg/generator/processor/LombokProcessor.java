package com.shzisg.generator.processor;

import com.shzisg.generator.output.DomainDefine;
import com.shzisg.generator.output.EntityDefine;

public class LombokProcessor implements Processor {

    @Override
    public DomainDefine process(DomainDefine domainDefine) {
        if (!domainDefine.isLombok()) {
            return domainDefine;
        }
        for (EntityDefine entityDefine : domainDefine.getEntities()) {
            entityDefine.addDecorator("@Data");
            entityDefine.addImport("lombok.Data");
            entityDefine.setSetter(false);
            entityDefine.setGetter(false);
            entityDefine.setEquals(false);
            entityDefine.setHashCode(false);
            entityDefine.setToString(false);
        }
        return domainDefine;
    }
}

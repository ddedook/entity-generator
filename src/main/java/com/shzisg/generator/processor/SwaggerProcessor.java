package com.shzisg.generator.processor;

import com.shzisg.generator.output.DomainDefine;
import com.shzisg.generator.output.EntityDefine;

public class SwaggerProcessor implements Processor {

    @Override
    public DomainDefine process(DomainDefine domainDefine) {
        if (!domainDefine.isSwagger()) {
            return domainDefine;
        }

        for (EntityDefine entityDefine : domainDefine.getEntities()) {
            entityDefine.addImport("io.swagger.annotations.ApiModel");
            entityDefine.addImport("io.swagger.annotations.ApiModelProperty");
            entityDefine.addDecorator("@ApiModel(value = \"" + entityDefine.getTableConfig().getComment() + "\")");
            entityDefine.getProperties().forEach(props -> {
                props.addDecorator("@ApiModelProperty(required = " + String.valueOf(!props.getColumnConfig().isNullable())
                        + ", value = \"" + props.getColumnConfig().getComment() + "\")");
            });
        }
        return domainDefine;
    }
}

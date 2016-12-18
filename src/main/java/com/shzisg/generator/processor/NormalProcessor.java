package com.shzisg.generator.processor;

import com.shzisg.generator.config.ColumnConfig;
import com.shzisg.generator.output.DomainDefine;
import com.shzisg.generator.output.EntityDefine;
import com.shzisg.generator.output.PropertyDefine;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.shzisg.generator.util.StringUtils.toCamel;

public class NormalProcessor implements Processor {

    @Override
    public DomainDefine process(DomainDefine domainDefine) {
        for (EntityDefine define : domainDefine.getEntities()) {
            define.setName(toCamel(define.getTableConfig().getName(), false) + define.getTableConfig().getSuffix());
            for (PropertyDefine prop : define.getProperties()) {
                ColumnConfig config = prop.getColumnConfig();
                prop.setName(toCamel(config.getName(), true));
                if (config.getWrapperType() == null) {
                    if (config.getType().contains(".")) {
                        define.addImport(config.getType());
                        prop.setType(config.getType().substring(config.getType().lastIndexOf(".") + 1));
                    } else {
                        prop.setType(config.getType());
                    }
                } else {
                    String wrapper = config.getWrapperType().substring(0, config.getWrapperType().indexOf("<"));
                    define.addImport(wrapper);
                    String propType = wrapper.substring(wrapper.lastIndexOf(".") + 1);
                    String[] types = StringUtils.substringBetween(config.getWrapperType(), "<", ">").split(",");
                    propType += Stream.of(types)
                            .map(String::trim)
                            .filter(define::addImport)
                            .collect(Collectors.joining(",", "<", ">"));
                    prop.setType(propType);
                }
            }
        }
        return domainDefine;
    }
}

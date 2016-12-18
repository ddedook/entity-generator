package com.shzisg.generator.processor;

import com.shzisg.generator.output.DomainDefine;
import com.shzisg.generator.output.EntityDefine;

public class DatabaseProcessor implements Processor {

    @Override
    public DomainDefine process(DomainDefine domainDefine) {
        for (EntityDefine entityDefine : domainDefine.getEntities()) {
            entityDefine.addImport("javax.persistence.*");
            entityDefine.addDecorator("@Entity");
            entityDefine.addDecorator("@Table(name = \"" + entityDefine.getTableConfig().getName() + "\")");
            entityDefine.getProperties().forEach(props -> {
                if (props.getColumnConfig().isPrimary()) {
                    props.addDecorator("@Id")
                            .addDecorator("@Column(name = \"" + props.getName() + "\")")
                            .addDecorator("@GeneratedValue(generator = \"generator\")")
                            .addDecorator("@GenericGenerator(name = \"generator\", strategy = \"uuid\")");
                } else if (!props.getColumnConfig().isExtend()) {
                    props.addDecorator("@Basic")
                            .addDecorator("@Column(name = \"" + props.getColumnConfig().getName() + "\")");
                } else {
                    props.addDecorator("@Transient");
                }
            });
        }
        return domainDefine;
    }
}

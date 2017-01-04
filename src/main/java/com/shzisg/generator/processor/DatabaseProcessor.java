package com.shzisg.generator.processor;

import com.shzisg.generator.output.DomainDefine;
import com.shzisg.generator.output.EntityDefine;

public class DatabaseProcessor implements Processor {
  
  @Override
  public DomainDefine process(DomainDefine domainDefine) {
    for (EntityDefine entityDefine : domainDefine.getEntities()) {
      System.out.println("[INFO] Generate " + entityDefine.getTableConfig().getName() + " ===> " + entityDefine.getName());
      entityDefine.addImport("javax.persistence.*");
      entityDefine.addImport("java.io.Serializable");
      entityDefine.addDecorator("@Table(name = \"" + entityDefine.getTableConfig().getName() + "\")");
      entityDefine.getProperties().forEach(props -> {
        if (props.getColumnConfig().isPrimary()) {
          props.addDecorator("@Id")
            .addDecorator("@Column(name = \"" + props.getColumnConfig().getName() + "\")")
            .addDecorator("@GeneratedValue(generator = \"uuid\")");
        } else if (!props.getColumnConfig().isExtend()) {
          props.addDecorator("@Column(name = \"" + props.getColumnConfig().getName() + "\")");
        } else {
          props.addDecorator("@Transient");
        }
      });
    }
    return domainDefine;
  }
}

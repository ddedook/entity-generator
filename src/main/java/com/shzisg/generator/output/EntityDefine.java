package com.shzisg.generator.output;

import com.shzisg.generator.config.TableConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityDefine {
  private String packagePath;
  private List<String> imports = new ArrayList<>();
  private String name;
  private TableConfig tableConfig;
  private List<String> decorators = new ArrayList<>();
  private List<PropertyDefine> properties = new ArrayList<>();
  private boolean setter = true;
  private boolean getter = true;
  private boolean toString = false;
  private boolean equals = true;
  private boolean hashCode = true;
  
  public String getPackagePath() {
    return packagePath;
  }
  
  public void setPackagePath(String packagePath) {
    this.packagePath = packagePath;
  }
  
  public List<String> getImports() {
    return imports;
  }
  
  public boolean addImport(String type) {
    if ((type.startsWith("java.io.") && !type.equals("java.io.Serializable"))
      || type.startsWith("java.math.")
      || type.startsWith("java.lang.")
      || this.imports.contains(type)) {
      return true;
    }
    this.imports.add(type);
    return true;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public TableConfig getTableConfig() {
    return tableConfig;
  }
  
  public void setTableConfig(TableConfig tableConfig) {
    this.tableConfig = tableConfig;
  }
  
  public List<String> getDecorators() {
    return decorators;
  }
  
  public void addDecorator(String decorator) {
    this.decorators.add(decorator);
  }
  
  public List<PropertyDefine> getProperties() {
    return properties;
  }
  
  public void setProperties(List<PropertyDefine> properties) {
    this.properties = properties;
  }
  
  public void addProperties(PropertyDefine property) {
    this.properties.add(property);
  }
  
  public boolean isSetter() {
    return setter;
  }
  
  public void setSetter(boolean setter) {
    this.setter = setter;
  }
  
  public boolean isGetter() {
    return getter;
  }
  
  public void setGetter(boolean getter) {
    this.getter = getter;
  }
  
  public boolean isToString() {
    return toString;
  }
  
  public void setToString(boolean toString) {
    this.toString = toString;
  }
  
  public boolean isEquals() {
    return equals;
  }
  
  public void setEquals(boolean equals) {
    this.equals = equals;
  }
  
  public boolean isHashCode() {
    return hashCode;
  }
  
  public void setHashCode(boolean hashCode) {
    this.hashCode = hashCode;
  }
}

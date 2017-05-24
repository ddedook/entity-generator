package com.shzisg.generator.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DomainConfig {
    private String url;
    private String driver;
    private String schema;
    private String username;
    private String password;
    private String domainPackage;
    private boolean all = true;
    private String suffix = "Entity";
    private String tablePrefix = "";
    private List<TableConfig> tables = new ArrayList<>();
    private List<Pattern> excludes = new ArrayList<>();
    private List<String> defaultIgnores = new ArrayList<>();
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getDriver() {
        return driver;
    }
    
    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    public String getSchema() {
        return schema;
    }
    
    public void setSchema(String schema) {
        this.schema = schema;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getDomainPackage() {
        return domainPackage;
    }
    
    public void setDomainPackage(String domainPackage) {
        this.domainPackage = domainPackage;
    }
    
    public boolean isAll() {
        return all;
    }
    
    public void setAll(boolean all) {
        this.all = all;
    }
    
    public List<TableConfig> getTables() {
        return tables;
    }
    
    public void setTables(List<TableConfig> tables) {
        this.tables = tables;
    }
    
    public List<String> getDefaultIgnores() {
        return defaultIgnores;
    }
    
    public void setDefaultIgnores(List<String> defaultIgnores) {
        this.defaultIgnores = defaultIgnores;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    public String getTablePrefix() {
        return tablePrefix;
    }
    
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
    
    public List<Pattern> getExcludes() {
        return excludes;
    }
    
    public void setExcludes(List<String> excludes) {
        this.excludes = excludes.stream()
            .map(Pattern::compile)
            .collect(Collectors.toList());
    }
    
    public boolean isExclude(String tableName) {
        return excludes.stream()
            .anyMatch(pattern -> pattern.matcher(tableName).lookingAt());
    }
}

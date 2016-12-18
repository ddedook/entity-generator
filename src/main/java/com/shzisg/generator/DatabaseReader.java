package com.shzisg.generator;

import com.shzisg.generator.config.ColumnConfig;
import com.shzisg.generator.config.DomainConfig;
import com.shzisg.generator.config.TableConfig;
import com.shzisg.generator.output.EntityDefine;
import com.shzisg.generator.output.PropertyDefine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseReader {

    private TypeMapper mapper;

    public void setMapper(TypeMapper mapper) {
        this.mapper = mapper;
    }

    public List<EntityDefine> readFrom(DomainConfig domain) {
        Connection conn = null;
        try {
            Class.forName(domain.getDriver());
            conn = DriverManager.getConnection(domain.getUrl(), domain.getUsername(), domain.getPassword());
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableSet = metaData.getTables(null, domain.getSchema(), "%", new String[]{"TABLE"});
            Map<String, TableConfig> tablesConfig = domain.getTables()
                    .stream()
                    .collect(Collectors.toMap(TableConfig::getName, t -> t));
            List<EntityDefine> entityDefines = new ArrayList<>();
            while (tableSet.next()) {
                String tableName = tableSet.getString("TABLE_NAME");
                if (!domain.isAll() && !tablesConfig.containsKey(tableName)) {
                    continue;
                }

                EntityDefine entityDefine = new EntityDefine();
                entityDefine.setPackagePath(domain.getDomainPackage());
                TableConfig tableConfig = tablesConfig.getOrDefault(tableName, new TableConfig());
                entityDefine.setTableConfig(tableConfig);
                tableConfig.setName(tableName);
                if (tableConfig.getComment() == null) {
                    tableConfig.setComment(tableSet.getString(5));
                }

                // get primary columns
                ResultSet primarySet = metaData.getPrimaryKeys(null, domain.getSchema(), tableName);
                List<String> primaryColumns = new ArrayList<>();
                while (primarySet.next()) {
                    primaryColumns.add(primarySet.getString("COLUMN_NAME"));
                }
                // get columns
                ResultSet columnSet = metaData.getColumns(null, domain.getSchema(), tableName, null);
                Map<String, ColumnConfig> columnsConfig;
                if (tableConfig.getColumns() == null) {
                    columnsConfig = new HashMap<>();
                } else {
                    columnsConfig = tableConfig.getColumns()
                            .stream()
                            .collect(Collectors.toMap(ColumnConfig::getName, c -> c));
                }
                while (columnSet.next()) {
                    String columnName = columnSet.getString("COLUMN_NAME");
                    ColumnConfig columnConfig = columnsConfig.getOrDefault(columnName, new ColumnConfig(columnName));
                    if (columnConfig.getType() == null) {
                        columnConfig.setType(mapper.toJavaType(columnSet.getString("TYPE_NAME"),
                                columnSet.getInt("COLUMN_SIZE"), columnSet.getInt("DECIMAL_DIGITS")));
                    }
                    if (columnConfig.isPrimary() == null) {
                        columnConfig.setPrimary(primaryColumns.contains(columnName));
                    }
                    if (columnConfig.isNullable() == null) {
                        columnConfig.setNullable(columnSet.getString("NULLABLE").equals("0"));
                    }
                    if (columnConfig.getComment() == null) {
                        columnConfig.setComment(columnSet.getString("REMARKS"));
                    }
                    columnConfig.setExtend(false);
                    PropertyDefine propDef = new PropertyDefine();
                    propDef.setColumnConfig(columnConfig);
                    entityDefine.addProperties(propDef);
                }
                if (tableConfig.getExtend() != null) {
                    for (ColumnConfig columnConfig : tableConfig.getExtend()) {
                        PropertyDefine propDef = new PropertyDefine();
                        propDef.setColumnConfig(columnConfig);
                        columnConfig.setExtend(true);
                        entityDefine.addProperties(propDef);
                    }
                }
                entityDefines.add(entityDefine);
            }
            return entityDefines;
        } catch (Exception e) {
            throw new RuntimeException("Create entity info info failed", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {}
            }
        }
    }
}
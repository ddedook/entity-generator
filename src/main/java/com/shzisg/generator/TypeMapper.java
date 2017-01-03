package com.shzisg.generator;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.plexus.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeMapper {

    private Map<String, List<Mapper>> mappers;

    public TypeMapper() {
        InputStream inputStream = getClass().getResourceAsStream("/type-mapping.json");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String content = toString(inputStream);
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, Mapper.class);
            List<Mapper> mappers = objectMapper.readValue(content, type);
            this.mappers = mappers.stream()
                    .collect(Collectors.groupingBy(Mapper::getColumnType));
        } catch (IOException e) {
            throw new RuntimeException("read type mapping file failed", e);
        }
    }

    public static String toString(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            output.write(i);
        }
        return output.toString();
    }

    public void extend(File extendFile) {
        try {
            String extendConfig = FileUtils.fileRead(extendFile, "utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, Mapper.class);
            List<Mapper> extendMappers = objectMapper.readValue(extendConfig, type);
            for (Mapper extendMapper : extendMappers) {
                List<Mapper> mapperList = mappers.getOrDefault(extendMapper.getColumnType(), new ArrayList<>());
                boolean contains = false;
                for (Mapper mapper : mapperList) {
                    if (mapper.getColumnSize() == extendMapper.getColumnSize()
                            && mapper.isDecimalDigit() == extendMapper.isDecimalDigit()) {
                        mapper.setMappedType(extendMapper.getMappedType());
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    mapperList.add(extendMapper);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Read type extend file failed", e);
        }
    }

    public String toJavaType(String type, int columnSize, int decimalDigits) {
        List<Mapper> mapperList = this.mappers.get(type.toUpperCase());
        if (mapperList == null || mapperList.isEmpty()) {
            return this.mappers.get("DEFAULT").get(0).getMappedType();
        }
        for (Mapper mapper : mapperList) {
            if (mapper.getColumnSize() > columnSize
                    && ((decimalDigits > 0 && mapper.isDecimalDigit()) || (decimalDigits == 0 && !mapper.isDecimalDigit()))) {
                return mapper.getMappedType();
            }
        }
        return mapperList.get(0).getMappedType();
    }

    private static class Mapper {
        private String columnType;
        private int columnSize = -1;
        private boolean decimalDigit;
        private String mappedType;

        public String getColumnType() {
            return columnType;
        }

        public void setColumnType(String columnType) {
            this.columnType = columnType;
        }

        public int getColumnSize() {
            return columnSize;
        }

        public void setColumnSize(int columnSize) {
            this.columnSize = columnSize;
        }

        public boolean isDecimalDigit() {
            return decimalDigit;
        }

        public void setDecimalDigit(boolean decimalDigit) {
            this.decimalDigit = decimalDigit;
        }

        public String getMappedType() {
            return mappedType;
        }

        public void setMappedType(String mappedType) {
            this.mappedType = mappedType;
        }
    }
}

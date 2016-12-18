package com.shzisg.generator.util;

public class StringUtils {

    public static String toCamel(String name, boolean firstLower) {
        StringBuilder builder = new StringBuilder();
        if (name == null || name.isEmpty()) {
            return "";
        } else if (!name.contains("_")) {
            return firstLower ? name.toLowerCase() : name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }

        String[] words = name.split("_");
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            if (builder.length() == 0 && firstLower) {
                builder.append(word.toLowerCase());
            } else {
                builder.append(word.substring(0, 1).toUpperCase());
                builder.append(word.substring(1).toLowerCase());
            }
        }
        return builder.toString();
    }
}

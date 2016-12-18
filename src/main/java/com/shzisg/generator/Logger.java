package com.shzisg.generator;

import org.apache.maven.plugin.logging.Log;

public class Logger {
    private static Log log;

    public static void setLogger(Log log) {
        Logger.log = log;
    }

    public static void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void info(String message) {
        log.info(message);
    }
}

package org.kasun.website.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticLogger {

    private static Logger logger;

    public static void setLogger(Logger logger) {
        StaticLogger.logger = logger;
    }

    public static void info(String message) {
        log(Level.INFO, message);
    }

    public static void warning(String message) {
        log(Level.WARNING, message);
    }

    public static void severe(String message) {
        log(Level.SEVERE, message);
    }

    private static void log(Level level, String message) {
        if (logger != null) {
            logger.log(level, message);
        }
    }
}


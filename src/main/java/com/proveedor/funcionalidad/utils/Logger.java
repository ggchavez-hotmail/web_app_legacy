package com.proveedor.funcionalidad.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    String name;

    public Logger(String className) {
        name = className;
    }

    public static Logger getLogger(String className) {
        return new Logger(className);
    }

    public String getName() {
        return name;
    }

    public void info(String message) {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String logLine = String.format("[%s] [%s] [%s] %s", name, df1.format(new Date()), "INFO", message);
        System.out.println(logLine);
    }

    public void error(String message) {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String logLine = String.format("[%s] [%s] [%s] %s", name, df1.format(new Date()), "ERROR", message);
        System.out.println(logLine);
    }
}

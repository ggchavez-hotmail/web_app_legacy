package com.proveedor.funcionalidad.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Env {
    private static final Logger LOG = Logger.getLogger(Env.class.getName());

    private Env() {
        throw new IllegalStateException("Utility class");
    }

    public static String getEnv(String env) {
        String variable = null;
        try {
            variable = System.getenv(env);
            if (variable == null || variable.trim().equals("")) {
                InitialContext envWeb = new InitialContext();
                variable = String.valueOf(envWeb.lookup("java:comp/env/" + env));
            }
        } catch (NamingException e) {
            LOG.error(e.getMessage());
        }
        return variable;
    }
}

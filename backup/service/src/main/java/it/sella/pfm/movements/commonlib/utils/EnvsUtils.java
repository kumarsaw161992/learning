package it.sella.pfm.movements.commonlib.utils;

public class EnvsUtils {

    public static final String PRO = "PRO";
    public static final String PRE = "PRE";
    public static final String TEST = "TEST";
    public static final String DEV = "DEV";
    public static final String ENVIRONMENT = "Environment";

    private EnvsUtils() {
    }

    public static String getEnv() {
        return getProperty(ENVIRONMENT, DEV);
    }

    public static String getProperty(String key, String defaultValue) {
        String val = System.getProperty(key);
        return (val == null) ? defaultValue : val;
    }
}
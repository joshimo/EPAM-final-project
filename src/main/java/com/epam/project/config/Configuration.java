package com.epam.project.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class Configuration {

    private static Configuration instance = new Configuration();
    private ResourceBundle resource;

    private Configuration() {
        resource = ResourceBundle.getBundle("config");
    }

    public static Configuration getInstance() {
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}

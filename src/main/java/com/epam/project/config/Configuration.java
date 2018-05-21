package com.epam.project.config;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Configuration {

    private static Configuration instance = new Configuration();
    private ResourceBundle pages;
    private ResourceBundle errors;
    private Locale locale;

    private Configuration() {
        locale = Locale.getDefault();
        pages = ResourceBundle.getBundle("pages", locale);
        errors = ResourceBundle.getBundle("errors", locale);
    }

    public static Configuration getInstance() {
        return instance;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getPage(String key) {
        return (String) pages.getObject(key);
    }

    public String getErrorMessage(String key) {
        String message;
        try {
            message = new String(errors.getObject(key).toString().getBytes("iso-8859-1"), "windows-1251");
        } catch (UnsupportedEncodingException uex) {
            return errors.getObject(key).toString();
        }
        return message;
    }

    public Locale getLocale() {
        return locale;
    }
}
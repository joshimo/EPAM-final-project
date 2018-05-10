package com.epam.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SessionRequestContent {

    private String referer;
    private Map<String, Object> reqAttributes;
    private Map<String, String[]> reqParameters;
    private Map<String, Object> sessionAttributes;

    public SessionRequestContent(HttpServletRequest req) {
        sessionAttributes = new HashMap<>();
        reqParameters = req.getParameterMap();
        HttpSession currentSession = req.getSession(false);
        if (currentSession != null) {
            Enumeration<String> sessionAttributeNames = currentSession.getAttributeNames();
            while (sessionAttributeNames.hasMoreElements()) {
                String attributeName = sessionAttributeNames.nextElement();
                sessionAttributes.put(attributeName, currentSession.getAttribute(attributeName));
            }
        }
        try {
            referer = req.getHeader("Referer");
        } catch (NullPointerException npe) {}
    }

    public void updateRequest(HttpServletRequest request) {
        reqAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach(request.getSession()::setAttribute);
    }

    /** returns a refer to redirect */
    public String getReferer() {
        return referer;
    }

    public Object getRequestAttribute(String key) {
        return reqAttributes.get(key);
    }

    public String[] getRequestParameter(String key) {
        return reqParameters.get(key);
    }

    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nReferer: " + getReferer());
        /*Set<String> keys = reqAttributes.keySet();
        for (String key : keys)
            sb.append("\nRequest attribute: key = " + key + " value = " + getRequestAttribute(key));*/
        Set<String> keys = reqParameters.keySet();
        for (String key : keys)
            sb.append("\nRequest parameter: key = " + key + " value = " + getRequestParameter(key));
        keys = sessionAttributes.keySet();
        for (String key : keys)
            sb.append("\nSession attribute: key = " + key + " value = " + getSessionAttribute(key));
        return sb.toString();
    }
}

package com.epam.project.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/** Object of ExecutionResult Class is result of Command execution */
public class ExecutionResult {

    private String page;
    private Direction direction;
    private boolean isInvalidated;
    private Map<String, Object> sessionAttributes = new HashMap<>();
    private Map<String, Object> requestAttributes = new HashMap<>();
    private Map<String, Object> requestParameters = new HashMap<>();

    public ExecutionResult() {
    }

    public Object addRequestAttribute(String key, Object value) {
        return requestAttributes.put(key, value);
    }

    public Object addSessionAttribute(String key, Object value) {
        return sessionAttributes.put(key, value);
    }

    /*public Object addRequestParameter(String key, Object value) {
        return requestParameters.put(key, value);
    }*/

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isInvalidated() {
        return isInvalidated;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void updateRequest(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach(request.getSession()::setAttribute);
    }

    public void invalidateSession() {
        isInvalidated = true;
    }

}
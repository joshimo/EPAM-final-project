package com.epam.project.service;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {

    String execute(HttpServletRequest request);

}

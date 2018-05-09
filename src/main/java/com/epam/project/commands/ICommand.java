package com.epam.project.commands;

import javax.servlet.http.HttpServletRequest;

public interface ICommand {

    String execute(HttpServletRequest request);

}

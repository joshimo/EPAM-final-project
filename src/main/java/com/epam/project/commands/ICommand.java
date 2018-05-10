package com.epam.project.commands;

import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;

import javax.servlet.http.HttpServletRequest;

public interface ICommand {

    ExecutionResult execute(SessionRequestContent content);

}

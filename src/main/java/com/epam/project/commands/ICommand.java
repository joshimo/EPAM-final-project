package com.epam.project.commands;

import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;

import javax.servlet.http.HttpServletRequest;

public interface ICommand {
    /**
     * @param content - object that contains session and request attributes and parameters
     * @return object that contains new request and session attributes and parameters as the result of command execution
     */
    ExecutionResult execute(SessionRequestContent content);

}

package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;

import javax.servlet.http.HttpServletRequest;

public class CommandUserEnter implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent request) {
        ExecutionResult result = new ExecutionResult();
        result.setPage(Configuration.getInstance().getProperty("login"));
        result.setDirection(Direction.FORWARD);
        return result;
    }
}

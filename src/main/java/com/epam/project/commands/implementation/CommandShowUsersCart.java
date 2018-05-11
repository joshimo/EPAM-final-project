package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.exceptions.UnknownUserException;

public class CommandShowUsersCart implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            result.setPage(Configuration.getInstance().getProperty("usersCart"));
        }
        catch (NullPointerException uue) {
            result.setPage(Configuration.getInstance().getProperty("error"));
        }
        return result;
    }

}

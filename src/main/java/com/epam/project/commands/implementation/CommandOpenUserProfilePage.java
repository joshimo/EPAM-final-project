package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;

public class CommandOpenUserProfilePage implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent request) {
        ExecutionResult result = new ExecutionResult();
        result.setPage(Configuration.getInstance().getPage("userProfile"));
        result.setDirection(Direction.FORWARD);
        return result;
    }
}

package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserCart;
import com.epam.project.exceptions.UnknownUserException;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandLogout implements ICommand {

    private static final Logger log = Logger.getLogger(CommandLogout.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.REDIRECT);
        try {
            result.invalidateSession();
            result.setPage("/project");
        }
        catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("generalErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

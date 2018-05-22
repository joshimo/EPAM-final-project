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

public class CommandValidateUser implements ICommand {

    private static final Logger log = Logger.getLogger(CommandValidateUser.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        String login = content.getRequestParameter("name")[0];
        String password = content.getRequestParameter("password")[0];
        try {
            User user = ServiceFactory.getUserService().findUser(login, password);
            UserCart cart = new UserCart(user.getName());
            result.addSessionAttribute("user", user);
            result.addSessionAttribute("cart", cart);
            result.setPage(conf.getPage("redirect_home"));
        }
        catch (UnknownUserException uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("validateUserErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}

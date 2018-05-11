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

public class CommandValidateUser implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        String login = content.getRequestParameter("name")[0];
        String password = content.getRequestParameter("password")[0];
        try {
            User user = ServiceFactory.getUserService().findUser(login, password);
            UserCart cart = new UserCart(user.getName());
            result.addSessionAttribute("user", user);
            result.addSessionAttribute("cart", cart);
            result.setPage("/project?command=main");
        }
        catch (UnknownUserException uue) {
            result.setPage(Configuration.getInstance().getProperty("error"));
        }
        return result;
    }
}

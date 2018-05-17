package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserCart;
import com.epam.project.exceptions.UnknownUserException;
import com.epam.project.service.IUserServ;
import com.epam.project.service.ServiceFactory;

public class CommandMissing implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IUserServ userServ = ServiceFactory.getUserService();
            User guest = userServ.findUser("Guest", "");
            UserCart cart = new UserCart(guest.getName());
            result.addSessionAttribute("user", guest);
            result.addSessionAttribute("cart", cart);
            result.addSessionAttribute("config", Configuration.getInstance());
            result.addRequestAttribute("command", "main");
            result.addRequestAttribute("pageNum", 1);
            result.setPage("/project?command=main&pageNum=1");
        } catch (UnknownUserException pse) {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("generalErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

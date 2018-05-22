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
import org.apache.log4j.Logger;

import java.util.Locale;

public class CommandMissing implements ICommand {

    private static final Logger log = Logger.getLogger(CommandMissing.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IUserServ userServ = ServiceFactory.getUserService();
            User guest = userServ.findUser("Guest", "");
            UserCart cart = new UserCart(guest.getName());
            if (!content.checkSessionAttribute("user"))
                result.addSessionAttribute("user", guest);
            if (!content.checkSessionAttribute("cart"))
                result.addSessionAttribute("cart", cart);
            if (!content.checkSessionAttribute("local"))
                result.addSessionAttribute("locale", new Locale("ru", "RU"));
            result.addRequestAttribute("pageNum", 1);
            result.setPage(conf.getPage("redirect_home"));
        } catch (UnknownUserException uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("generalErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

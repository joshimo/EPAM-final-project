package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.domain.User;
import com.epam.project.exceptions.UnknownUserException;
import com.epam.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

public class CommandLogin implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login =  "Yaroslav"/*request.getParameter("login")*/;
        String password = "yaroslav"/*request.getParameter("password")*/;
        try {
            User user = ServiceFactory.getUserService().findUser(login, password);
            request.getSession().setAttribute("user", user);
            page = Configuration.getInstance().getProperty("main");
        }
        catch (UnknownUserException uue) {
            page = Configuration.getInstance().getProperty("error");
        }
        return page;
    }
}

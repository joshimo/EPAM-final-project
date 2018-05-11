package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserCart;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.UnknownUserException;
import com.epam.project.service.IUserServ;
import com.epam.project.service.ServiceFactory;

public class CommandSaveNewUser implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        String login = content.getRequestParameter("name")[0];
        String password = content.getRequestParameter("password")[0];
        String email = content.getRequestParameter("email")[0];
        String notes = content.getRequestParameter("notes")[0];
        try {
            User user = new User(login, password);
            user.setEmail(email);
            user.setNotes(notes);
            user.setUserRole(UserRole.USER);
            IUserServ userServ = ServiceFactory.getUserService();
            if (userServ.addUser(user))
                result.setPage("/project?command=main");
            else
                result.setPage(Configuration.getInstance().getProperty("error"));
        }
        catch (Exception uue) {
            result.setPage(Configuration.getInstance().getProperty("error"));
        }
        return result;
    }
}

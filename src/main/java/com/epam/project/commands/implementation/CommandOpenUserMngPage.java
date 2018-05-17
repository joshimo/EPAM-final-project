package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.service.IUserServ;
import com.epam.project.service.ServiceFactory;

import java.util.LinkedList;
import java.util.List;

public class CommandOpenUserMngPage implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            String type = (String) content.getRequestParameter("type")[0];
            IUserServ serv = ServiceFactory.getUserService();
            List<User> users = new LinkedList<>();
            if (type.equals("all"))
                users = serv.findAllUsers();
            if (type.equals("user"))
                users = serv.findUsersByRole(UserRole.USER);
            if (type.equals("cashier"))
                users = serv.findUsersByRole(UserRole.CASHIER);
            if (type.equals("seniorCashier"))
                users = serv.findUsersByRole(UserRole.SENIOR_CASHIER);
            if (type.equals("merchant"))
                users = serv.findUsersByRole(UserRole.MERCHANT);
            if (type.equals("admin"))
                users = serv.findUsersByRole(UserRole.ADMIN);
            result.addRequestAttribute("users", users);
            result.setPage(conf.getPage("manageUsers"));
        }
        catch (Exception e) {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("manageUsersErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
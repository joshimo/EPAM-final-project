package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.service.IUserServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandSaveUserProfile implements ICommand {

    private static final Logger log = Logger.getLogger(CommandSaveUserProfile.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            String userId = content.getRequestParameter("userId")[0];
            String login = content.getRequestParameter("name")[0];
            String password = content.getRequestParameter("password")[0];
            String phone = content.getRequestParameter("phone")[0];
            String email = content.getRequestParameter("email")[0];
            String address = content.getRequestParameter("address")[0];
            String notes = content.getRequestParameter("notes")[0];
            User user = new User(login, password);
            user.setId(Integer.parseInt(userId));
            user.setPhoneNumber(phone);
            user.setEmail(email);
            user.setAddress(address);
            user.setNotes(notes);
            user.setUserRole(UserRole.USER);
            IUserServ userServ = ServiceFactory.getUserService();
            if (userServ.updateUser(user)) {
                result.setPage(conf.getPage("redirect_home"));
                result.addSessionAttribute("user", user);
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveUserProfileErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        }
        catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveUserProfileErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
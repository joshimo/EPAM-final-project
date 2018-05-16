package com.epam.project.commands;

import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;

public class Security {
    /**
     * Checks if user allowed for action
     * @param content - object that contains session and request attributes and parameters
     * @param roles - user roles allowed for command execution
     * @return
     */
    public static boolean checkSecurity(SessionRequestContent content, UserRole... roles) {
        if (!content.checkSessionAttribute("user")) {
            return false;
        }
        User user = (User) content.getSessionAttribute("user");
        for (UserRole role : roles)
            if (user.getUserRole() == role)
                return true;
        return false;
    }
}

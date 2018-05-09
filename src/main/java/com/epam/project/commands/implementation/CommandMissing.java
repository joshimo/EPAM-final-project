package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;

import javax.servlet.http.HttpServletRequest;

public class CommandMissing implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        return Configuration.getInstance().getProperty("main");
    }

}

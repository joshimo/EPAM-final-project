package com.epam.project.controller;

import com.epam.project.commands.ICommand;
import com.epam.project.commands.implementation.CommandLogin;
import com.epam.project.commands.implementation.CommandMissing;
import com.epam.project.commands.implementation.CommandUserEnter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandResolver {

    private static CommandResolver instance = null;
    Map<String, ICommand> commands = new HashMap<String, ICommand>();

    public CommandResolver() {
        commands.put("login", new CommandLogin());
        commands.put("enter", new CommandUserEnter());
    }

    public ICommand getCommand(HttpServletRequest request) {
        System.out.println("command = " + request.getParameter("command"));
        ICommand command = commands.get(request.getParameter("command"));
        if (command == null)
            command = new CommandMissing();

        return command;
    }

    public static CommandResolver getInstance() {
        if (instance == null)
            instance = new CommandResolver();
        return instance;
    }
}

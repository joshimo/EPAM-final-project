package com.epam.project.controller;

import com.epam.project.commands.ICommand;
import com.epam.project.commands.implementation.CommandLogin;
import com.epam.project.commands.implementation.CommandMissing;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestResolver {

    private static RequestResolver instance = null;
    Map<String, ICommand> commands = new HashMap<String, ICommand>();

    public RequestResolver() {
        commands.put("/login", new CommandLogin());
    }

    public ICommand getCommand(HttpServletRequest request) {
        ICommand command = commands.get(request.getPathInfo());
        if (command == null)
            command = new CommandMissing();

        return command;
    }

    public static RequestResolver getInstance() {
        if (instance == null)
            instance = new RequestResolver();
        return instance;
    }
}

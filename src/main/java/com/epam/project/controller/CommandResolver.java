package com.epam.project.controller;

import com.epam.project.commands.ICommand;
import com.epam.project.commands.implementation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandResolver {

    private static CommandResolver instance = null;
    Map<String, ICommand> commands = new HashMap<>();

    public CommandResolver() {
        commands.put("login", new CommandValidateUser());
        commands.put("enter", new CommandOpenLoginPage());
        commands.put("main", new CommandShowMainPage());
        commands.put("usersCart", new CommandShowUsersCart());
        commands.put("addProductToCart", new CommandAddToCart());
        commands.put("removeProductFromCart", new CommandRemoveFromCart());
        commands.put("createInvoice", new CommandCreateInvoice());
        commands.put("createInvoiceAndPay", new CommandCreateInvoiceAndPay());
        commands.put("addNewUser", new CommandOpenRegistrationPage());
        commands.put("registerNewUser", new CommandSaveNewUser());
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
package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;

import java.util.Locale;

public class CommandChangeLang implements ICommand {
    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.REDIRECT);
        String lang = content.getRequestParameter("lang")[0];
        if (lang.equals("en"))
            result.addSessionAttribute("locale", new Locale("en", "EN"));
        else
            result.addSessionAttribute("locale", new Locale("ru", "RU"));
        result.setPage(content.getReferer());
        return result;
    }
}
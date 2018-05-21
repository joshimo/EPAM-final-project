package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;

import java.util.Locale;
import java.util.Map;

public class CommandChangeLang implements ICommand {
    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        Map<String, String[]> parameters = content.getRequestParameters();
        String lang = parameters.get("lang")[0];
        if (lang.toLowerCase().equals("en"))
            result.addSessionAttribute("locale", new Locale("en", "EN"));
        if (lang.toLowerCase().equals("ru"))
            result.addSessionAttribute("locale", new Locale("ru", "RU"));
        parameters.forEach((key, value) -> {
            if (!key.toLowerCase().equals("command"))
                result.addRequestParameter(key, value[0]);});
        result.setDirection(Direction.REDIRECT);
        result.addRequestParameter("command", content.getRequestParameter("returnPage")[0]);
        result.setPage(conf.getPage("base"));
        result.addParametersToPage();
        return result;
    }
}
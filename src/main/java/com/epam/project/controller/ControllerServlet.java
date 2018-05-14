package com.epam.project.controller;

import com.epam.project.commands.ICommand;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(ControllerServlet.class);
    private CommandResolver commandResolver = CommandResolver.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionRequestContent content = new SessionRequestContent(req);
        log.info(content);
        ICommand command = commandResolver.getCommand(req);
        ExecutionResult result = command.execute(content);
        result.updateRequest(req);
        if (result.getDirection() == Direction.FORWARD)
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        if (result.getDirection() == Direction.REDIRECT)
            req.getRequestDispatcher(req.getContextPath() + result.getPage());
    }
}

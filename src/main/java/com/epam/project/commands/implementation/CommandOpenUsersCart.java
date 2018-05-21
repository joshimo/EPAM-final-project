package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.UserCart;
import com.epam.project.domain.UserCartView;
import com.epam.project.exceptions.InvoiceServiceException;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandOpenUsersCart implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenUsersCart.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            UserCartView view = serv.createUsersCartView((UserCart) content.getSessionAttribute("cart"));
            result.addRequestAttribute("cartView", view);
            result.setPage(conf.getPage("usersCart"));
        }
        catch (NullPointerException | InvoiceServiceException uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showUserCartErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }

}

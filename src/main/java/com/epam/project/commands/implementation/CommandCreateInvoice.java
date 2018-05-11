package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserCart;
import com.epam.project.exceptions.InvoiceServiceException;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.implementation.InvoiceService;

public class CommandCreateInvoice implements ICommand {
    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            Long invoiceCode = System.currentTimeMillis();
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            IInvoiceServ invoiceServ = ServiceFactory.getInvoiceService();
            Invoice invoice = invoiceServ.createInvoiceFromUserCart(cart, invoiceCode);
            if (invoiceServ.addInvoice(invoice))
                result.setPage("/project?command=main");
            else
                result.setPage(Configuration.getInstance().getProperty("error"));
        }
        catch (NullPointerException | InvoiceServiceException npe) {
            npe.printStackTrace();
            result.setPage(Configuration.getInstance().getProperty("error"));
        }
        return result;
    }
}

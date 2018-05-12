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

public class CommandCreateInvoiceAndPay implements ICommand {
    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            Long invoiceCode = System.currentTimeMillis();
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            IInvoiceServ invoiceServ = ServiceFactory.getInvoiceService();
            Invoice invoice = invoiceServ.createInvoiceFromUserCart(cart, invoiceCode);
            if (invoiceServ.addInvoice(invoice) && invoiceServ.payByInvoice(invoiceCode))
                result.setPage("/project?command=main");
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("invoiceCreationErr"));
                result.setPage(conf.getPage("error"));
            }
        }
        catch (NullPointerException | InvoiceServiceException npe) {
            npe.printStackTrace();
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("invoiceCreationErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

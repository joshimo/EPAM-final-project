package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.ServiceFactory;

public class CommandCloseInvoice implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            Long invCode = Long.parseLong(content.getRequestParameter("invCode")[0]);
            if (serv.closeInvoice(invCode)) {
                Invoice invoice = serv.findInvoiceByOrderNumber(invCode);
                result.addRequestAttribute("invoice", invoice);
                result.setPage(conf.getPage("invoiceDetails"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("removefromCartErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        }
        catch (NullPointerException npe) {
            npe.printStackTrace();
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("removefromCartErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}

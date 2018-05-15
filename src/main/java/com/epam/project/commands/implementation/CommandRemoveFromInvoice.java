package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserCart;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.ServiceFactory;

public class CommandRemoveFromInvoice implements ICommand {
    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            Long invCode = Long.parseLong((String) content.getRequestParameter("invCode")[0]);
            String productCode = (String) content.getRequestParameter("productCode")[0];
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            if (serv.removeProductFromInvoice(invCode, productCode)) {
                Invoice inv = serv.findInvoiceByOrderNumber(invCode);
                result.addRequestAttribute("invoice", inv);
                result.addRequestAttribute("command", "showInvoiceDetail");
                result.addRequestAttribute("code", invCode);
                result.setPage(conf.getPage("invoiceDetails"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("removefromCartErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("removefromCartErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}

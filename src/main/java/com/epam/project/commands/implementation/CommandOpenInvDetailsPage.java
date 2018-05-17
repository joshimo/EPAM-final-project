package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserRole;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.ServiceFactory;

import java.util.LinkedList;
import java.util.List;

public class CommandOpenInvDetailsPage implements ICommand {
    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.CASHIER, UserRole.SENIOR_CASHIER, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            Long code = Long.parseLong(content.getRequestParameter("code")[0]);
            Invoice invoice = serv.findInvoiceByOrderNumber(code);
            result.addRequestAttribute("invoice", invoice);
            result.setPage(conf.getPage("invoiceDetails"));
        }
        catch (Exception e) {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showInvoiceDetailsErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

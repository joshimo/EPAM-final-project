package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.Transaction;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.ITransactionServ;
import com.epam.project.service.ServiceFactory;

import java.util.LinkedList;
import java.util.List;

public class CommandOpenInvoiceMngPage implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            String type = (String) content.getRequestParameter("type")[0];
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            List<Invoice> invoices = new LinkedList<>();
            if (type.equals("all"))
                invoices = serv.findAllInvoices();
            result.addRequestAttribute("invoices", invoices);
            result.setPage(conf.getPage("manageInvoices"));
        }
        catch (Exception e) {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showMainPageErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
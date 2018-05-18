package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.InvoiceStatus;
import com.epam.project.domain.Payment;
import com.epam.project.service.IPaymentServ;
import com.epam.project.service.ServiceFactory;

public class CommandAddNewPayment implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IPaymentServ serv = ServiceFactory.getPaymentService();
            Payment payment = new Payment();
            payment.setProductCode(content.getRequestParameter("productCode")[0]);
            payment.setQuantity(Double.parseDouble(content.getRequestParameter("quantity")[0]));
            payment.setPaymentValue(Double.parseDouble(content.getRequestParameter("paymentValue")[0]));
            payment.setStatusId(InvoiceStatus.CREATED);
            payment.setOrderCode(Long.parseLong(content.getRequestParameter("orderCode")[0]));
            payment.setPaymentNotes(content.getRequestParameter("paymentNotes")[0]);
            if (serv.addPayment(payment))
                result.setPage(conf.getPage("manageInvoices"));
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveNewUserErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        } catch (Exception uue) {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveNewUserErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}

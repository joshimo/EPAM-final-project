package com.epam.project.commands.implementation;

import com.epam.project.commands.DataValidator;
import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.InvoiceStatus;
import com.epam.project.domain.Payment;
import com.epam.project.exceptions.InvalidValueException;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.IPaymentServ;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Set;

public class CommandAddNewPayment implements ICommand {

    private static final Logger log = Logger.getLogger(CommandAddNewPayment.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        IInvoiceServ invserv = ServiceFactory.getInvoiceService();
        IProductServ prodServ = ServiceFactory.getProductService();
        result.setDirection(Direction.FORWARD);
        try {
            IPaymentServ serv = ServiceFactory.getPaymentService();
            Payment payment = new Payment();
            payment.setProductCode(content.getRequestParameter("productCode")[0]);
            payment.setQuantity(DataValidator.filterDouble(content.getRequestParameter("quantity")[0]));
            payment.setStatusId(InvoiceStatus.CREATED);
            payment.setOrderCode(Long.parseLong(content.getRequestParameter("orderCode")[0]));
            payment.setPaymentNotes(content.getRequestParameter("paymentNotes")[0]);
            if (serv.addPayment(payment)) {
                Invoice invoice = invserv.findInvoiceByOrderNumber(payment.getOrderCode());
                Set<String> products = prodServ.createProductSet();
                result.addRequestAttribute("invoice", invoice);
                result.addRequestAttribute("products", products);
                result.setPage(conf.getPage("invoiceDetails"));
            }
            else {
                result.setDirection(Direction.FORWARD);
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("addNewPaymentErr"));
                result.setPage(conf.getPage("error"));
            }
        } catch (InvalidValueException ive) {
            log.error(ive);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("dataValidationError"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }  catch (Exception uue) {
            log.error(uue);
            result.setDirection(Direction.FORWARD);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("addNewPaymentErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

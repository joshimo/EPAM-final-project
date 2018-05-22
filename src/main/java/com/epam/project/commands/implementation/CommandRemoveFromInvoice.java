package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserCart;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.ProductServiceException;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Set;

public class CommandRemoveFromInvoice implements ICommand {

    private static final Logger log = Logger.getLogger(CommandRemoveFromInvoice.class);

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
            Long invCode = Long.parseLong((String) content.getRequestParameter("invCode")[0]);
            String productCode = (String) content.getRequestParameter("productCode")[0];
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            if (serv.removeProductFromInvoice(invCode, productCode)) {
                Invoice inv = serv.findInvoiceByOrderNumber(invCode);
                IProductServ prodServ = ServiceFactory.getProductService();
                Set<String> products = prodServ.createProductSet();
                result.addRequestAttribute("invoice", inv);
                result.addRequestAttribute("products", products);
                result.addRequestAttribute("command", "showInvoiceDetail");
                result.addRequestAttribute("code", invCode);
                result.setPage(conf.getPage("invoiceDetails"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("removeProductFromInvoiceErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        } catch (ProductServiceException | NullPointerException npe) {
            log.error(npe);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("removeProductFromInvoiceErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}

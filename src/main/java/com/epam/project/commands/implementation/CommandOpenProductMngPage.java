package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Product;
import com.epam.project.domain.UserRole;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;

public class CommandOpenProductMngPage implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenProductMngPage.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.MERCHANT, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            IProductServ serv = ServiceFactory.getProductService();
            Integer totalPages = (int) Math.floor(serv.calculateProductsNumber() / 5) + 1;
            Integer pageNum = content.checkRequestParameter("pageNum") ?
                    Integer.parseInt(content.getRequestParameter("pageNum")[0]) : 1;
            List<Product> products = serv.findProducts((pageNum - 1) * 5,5);
            result.addRequestAttribute("totalPages", totalPages);
            result.addRequestAttribute("pageNum", pageNum);
            result.addRequestAttribute("products", products);
            result.setPage(conf.getPage("manageProducts"));
        }
        catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("manageProductsErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

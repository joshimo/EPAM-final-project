package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.ProductServiceException;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;

public class CommandOpenMainPage implements ICommand{

    private static final Logger log = Logger.getLogger(CommandOpenMainPage.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IProductServ productServ = ServiceFactory.getProductService();
            Integer totalPages = (int) Math.floor(productServ.calculateProductsNumber() / 5) + 1;
            Integer pageNum = content.checkRequestParameter("pageNum") ?
                    Integer.parseInt(content.getRequestParameter("pageNum")[0]) : 1;
            List<Product> products = productServ.findProducts((pageNum - 1) * 5,5);
            result.addRequestAttribute("products", products);
            result.addRequestAttribute("totalPages", totalPages);
            result.addRequestAttribute("pageNum", pageNum);
            result.setPage(conf.getPage("main"));
        }
        catch (ProductServiceException uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showMainPageErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Product;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;

import java.util.List;

public class CommandOpenProductMngPage implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IProductServ serv = ServiceFactory.getProductService();
            List<Product> products = serv.findAllProducts();
            result.addRequestAttribute("products", products);
            result.setPage(conf.getPage("manageProducts"));
        }
        catch (Exception e) {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showMainPageErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

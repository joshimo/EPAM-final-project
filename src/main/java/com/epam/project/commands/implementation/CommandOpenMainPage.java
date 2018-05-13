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

import java.util.List;

public class CommandOpenMainPage implements ICommand{

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            //System.out.println("CommandOpenMainPage");
            IProductServ productServ = ServiceFactory.getProductService();
            List<Product> products = productServ.findAllProducts();
            result.addRequestAttribute("products", products);
            result.setPage(Configuration.getInstance().getPage("main"));
        }
        catch (ProductServiceException uue) {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showMainPageErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}

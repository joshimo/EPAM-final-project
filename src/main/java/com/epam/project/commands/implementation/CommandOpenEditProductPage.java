package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Product;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;

public class CommandOpenEditProductPage implements ICommand {
    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration config = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IProductServ serv = ServiceFactory.getProductService();
            String code = content.getRequestParameter("productCode")[0];
            Product product = serv.findProductByCode(code);
            result.addRequestAttribute("product", product);
            result.setPage(config.getPage("editProduct"));
        } catch (Exception e) {
            result.addRequestAttribute("errorMessage", config.getErrorMessage("saveNewUserErr"));
            result.setPage(config.getPage("error"));
        }
        return result;
    }
}

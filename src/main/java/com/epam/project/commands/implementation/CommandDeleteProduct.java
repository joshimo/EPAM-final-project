package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.UserRole;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandDeleteProduct implements ICommand {

    private static final Logger log = Logger.getLogger(CommandDeleteProduct.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        IProductServ serv = ServiceFactory.getProductService();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.MERCHANT, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            String productCode = (String) content.getRequestParameter("productCode")[0];
            if (serv.deleteProduct(productCode)) {
                result.setPage(conf.getPage("redirect_manageProducts"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("deleteProductErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        }
        catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("deleteProductErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
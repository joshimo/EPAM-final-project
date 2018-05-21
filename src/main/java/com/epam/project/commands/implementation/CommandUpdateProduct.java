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

public class CommandUpdateProduct implements ICommand {

    private static final Logger log = Logger.getLogger(CommandUpdateProduct.class);

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
            Product product = new Product();
            product.setId(Integer.parseInt(content.getRequestParameter("id")[0]));
            product.setCode(content.getRequestParameter("code")[0]);
            product.setNameRu(content.getRequestParameter("nameRu")[0]);
            product.setNameEn(content.getRequestParameter("nameEn")[0]);
            product.setDescriptionRu(content.getRequestParameter("descriptionRu")[0]);
            product.setDescriptionEn(content.getRequestParameter("descriptionEn")[0]);
            product.setCost(Double.parseDouble(content.getRequestParameter("cost")[0]));
            product.setAvailable(content.checkRequestParameter("isAvailable"));
            product.setQuantity(Double.parseDouble(content.getRequestParameter("quantity")[0]));
            product.setReservedQuantity(Double.parseDouble(content.getRequestParameter("reserved")[0]));
            product.setUomRu(content.getRequestParameter("uomRu")[0]);
            product.setUomEn(content.getRequestParameter("uomEn")[0]);
            product.setNotesRu(content.getRequestParameter("notesRu")[0]);
            product.setNotesEn(content.getRequestParameter("notesEn")[0]);
            if (serv.updateProduct(product)) {
                result.setPage("/project?command=manageProducts");
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("updateProductErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        }
        catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("updateProductErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}

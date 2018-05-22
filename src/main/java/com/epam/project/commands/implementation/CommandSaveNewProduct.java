package com.epam.project.commands.implementation;

import com.epam.project.commands.DataValidator;
import com.epam.project.commands.ICommand;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Product;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.InvalidValueException;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandSaveNewProduct implements ICommand {

    private static final Logger log = Logger.getLogger(CommandSaveNewProduct.class);

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
            product.setCode(content.getRequestParameter("code")[0]);
            product.setNameRu(content.getRequestParameter("nameRu")[0]);
            product.setNameEn(content.getRequestParameter("nameEn")[0]);
            product.setDescriptionRu(content.getRequestParameter("descriptionRu")[0]);
            product.setDescriptionEn(content.getRequestParameter("descriptionEn")[0]);
            product.setCost(DataValidator.filterDouble(content.getRequestParameter("cost")[0]));
            product.setAvailable(content.checkRequestParameter("isAvailable"));
            product.setQuantity(DataValidator.filterDouble(content.getRequestParameter("quantity")[0]));
            product.setUomRu(content.getRequestParameter("uomRu")[0]);
            product.setUomEn(content.getRequestParameter("uomEn")[0]);
            product.setNotesRu(content.getRequestParameter("notesRu")[0]);
            product.setNotesEn(content.getRequestParameter("notesEn")[0]);
            if (serv.addProduct(product)) {
                result.setPage(conf.getPage("redirect_manageProducts"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveNewProductErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        } catch (InvalidValueException ive) {
            log.error(ive);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("dataValidationError"));
            result.setPage(Configuration.getInstance().getPage("error"));
        } catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveNewProductErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.UserCart;
import org.apache.log4j.Logger;

public class CommandAddToCart implements ICommand {

    private static final Logger log = Logger.getLogger(CommandAddToCart.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        Configuration config = Configuration.getInstance();
        result.setDirection(Direction.FORWARD);
        try {
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            String productCode = content.getRequestParameter("productCode")[0];
            String productQuantity = content.getRequestParameter("productQuantity")[0];
            Double quantity = Double.parseDouble(filter(productQuantity));
            cart.addProduct(productCode, quantity);
            result.addSessionAttribute("cart", cart);
            result.setPage("/project?command=main");
        }
        catch (NullPointerException npe) {
            log.error(npe);
            result.addRequestAttribute("errorMessage", config.getErrorMessage("addToCartErr"));
            result.setPage(config.getPage("error"));
        }
        return result;
    }

    private String filter(String str) {
        return str.replaceAll(",", ".").replaceAll(" ", "");
    }
}

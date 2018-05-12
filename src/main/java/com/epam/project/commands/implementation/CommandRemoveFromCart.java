package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.UserCart;

public class CommandRemoveFromCart implements ICommand {
    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            String productCode = content.getRequestParameter("productCode")[0];
            cart.removeProduct(productCode);
            result.addSessionAttribute("cart", cart);
            result.setPage("/project?command=usersCart");
        }
        catch (NullPointerException npe) {
            npe.printStackTrace();
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("removefromCartErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}

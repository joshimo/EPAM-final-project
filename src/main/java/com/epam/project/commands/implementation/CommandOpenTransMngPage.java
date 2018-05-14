package com.epam.project.commands.implementation;

import com.epam.project.commands.ICommand;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Transaction;
import com.epam.project.domain.TransactionType;
import com.epam.project.service.ITransactionServ;
import com.epam.project.service.ServiceFactory;

import java.util.LinkedList;
import java.util.List;

public class CommandOpenTransMngPage implements ICommand {

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            String type = (String) content.getRequestParameter("type")[0];
            ITransactionServ serv = ServiceFactory.getTransactionService();
            List<Transaction> transactions = new LinkedList<>();
            if (type.equals("all"))
                transactions = serv.findAllTransactions();
            if (type.equals("payment"))
                transactions = serv.findAllTransactionsByType(TransactionType.PAYMENT);
            if (type.equals("refund"))
                transactions = serv.findAllTransactionsByType(TransactionType.REFUND);
            result.addRequestAttribute("transactions", transactions);
            result.setPage(conf.getPage("manageTransactions"));
        }
        catch (Exception e) {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showMainPageErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}

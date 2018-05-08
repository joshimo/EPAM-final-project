package com.epam.project.service;

import com.epam.project.service.implementation.InvoiceService;
import com.epam.project.service.implementation.ProductService;
import com.epam.project.service.implementation.TransactionService;
import com.epam.project.service.implementation.UserService;

public class ServiceFactory {

    public ServiceFactory() {
    }

    public static IUserServ getUserService() {
        return new UserService();
    }

    public static IProductServ getProductService() {
        return new ProductService();
    }

    public static ITransactionServ getTransactionService() {
        return new TransactionService();
    }

    public static IInvoiceServ getInvoiceService() {
        return new InvoiceService();
    }
}

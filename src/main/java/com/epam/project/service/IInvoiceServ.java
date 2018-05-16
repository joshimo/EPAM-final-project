package com.epam.project.service;

import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserCart;
import com.epam.project.exceptions.InvoiceServiceException;

import java.util.List;

public interface IInvoiceServ {

    @Button
    List<Invoice> findAllInvoices();

    @Button
    List<Invoice> findNewInvoices();

    @Button
    List<Invoice> findFinishedInvoices();

    @Button
    List<Invoice> findCancelledInvoices();

    @Button
    List<Invoice> findInvoicesByUser(String userName);

    @Button
    Invoice findInvoiceByOrderNumber(Long orderNum);

    @Button
    Invoice createInvoiceFromUserCart(UserCart userCart, Long orderCode) throws InvoiceServiceException;

    @Button
    boolean addInvoice(Invoice invoice);

    @Button
    boolean updateInvoice(Invoice invoice);

    @Button
    boolean deleteInvoice(Long orderCode);

    @Button
    boolean cancelInvoice(Long orderCode);

    @Button
    boolean closeInvoice(Long orderCode);

    @Button
    boolean removeProductFromInvoice(Long orderCode, String productCode);

    @Button
    boolean payByInvoice(Long invoiceCode);
}

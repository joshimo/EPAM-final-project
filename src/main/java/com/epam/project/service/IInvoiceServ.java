package com.epam.project.service;

import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserCart;
import com.epam.project.domain.UserCartView;
import com.epam.project.exceptions.InvoiceServiceException;

import java.util.List;

public interface IInvoiceServ {
    /**
     * Calculates total number of invoice records in DB
     * @return total number of invoices
     */
    Integer calculateInvoicesNumber();

    /**
     * Finds all invoices in DB
     * @return List of all invoices in DB
     */
    @Button
    List<Invoice> findAllInvoices();

    /**
     * Finds first @param=offset invoices starts from @param=from row
     * @param from first table line number
     * @param offset number or records to find
     * @return List of selected invoices in DB
     */
    @Button
    List<Invoice> findInvoices(Integer from, Integer offset);

    /**
     * Finds all new invoices in DB
     * @return List of selected invoices
     */
    @Button
    List<Invoice> findNewInvoices();

    /**
     * Finds all invoices with status "FINISHED"
     * @return List of selected invoices
     */
    @Button
    List<Invoice> findFinishedInvoices();

    /**
     * Finds all invoices with status "CANCELLED"
     * @return List of selected invoices
     */
    @Button
    List<Invoice> findCancelledInvoices();

    /**
     * Finds all invoices dy user
     * @param userName - user name
     * @return List of selected invoices
     */
    @Button
    List<Invoice> findInvoicesByUser(String userName);

    /**
     * Finds all invoices by order number
     * @param orderNum - order (invoice) number
     * @return Invoices by order number
     */
    @Button
    Invoice findInvoiceByOrderNumber(Long orderNum);

    /**
     * Creates an invoice from UserCart DTO
     * @param userCart - UserCart DTO
     * @param orderCode - unique order code of Invoice
     * @return created Invoice
     * @throws InvoiceServiceException if unable to retrieve information for certain reasons
     */
    @Button
    Invoice createInvoiceFromUserCart(UserCart userCart, Long orderCode) throws InvoiceServiceException;

    /**
     * Creates an UserCartView view DTO from UserCart object
     * @param userCart - UserCart DTO
     * @return created UserCartView
     * @throws InvoiceServiceException if unable to retrieve information for certain reasons
     */
    UserCartView createUsersCartView(UserCart userCart) throws InvoiceServiceException;

    /**
     * Puts new invoice in DB
     * @param invoice - invoice to add in DB
     * @return true if operation success and false if fails
     */
    @Button
    boolean addInvoice(Invoice invoice);

    /**
     * Updates exists invoice in DB
     * @param invoice - invoice to update in DB
     * @return true if operation success and false if fails
     */
    @Button
    boolean updateInvoice(Invoice invoice);

    /**
     * Deletes invoice from DB by order (invoice) code
     * @param orderCode - unique order (invoice) code of Invoice
     * @return true if operation success and false if fails
     */
    @Button
    boolean deleteInvoice(Long orderCode);

    /**
     * Sets to invoice status "CANCELLED"
     * @param orderCode - unique order (invoice) code of Invoice
     * @return true if operation success and false if fails
     */
    @Button
    boolean cancelInvoice(Long orderCode);

    /**
     * Sets to invoice status "CLOSED"
     * @param orderCode - unique order (invoice) code of Invoice
     * @return true if operation success and false if fails
     */
    @Button
    boolean closeInvoice(Long orderCode);

    /**
     * Removes a Payment from Invoice
     * @param orderCode - unique order (invoice) code of Invoice
     * @param productCode - code of Product
     * @return true if operation success and false if fails
     */
    @Button
    boolean removeProductFromInvoice(Long orderCode, String productCode);

    /**
     * Sets to invoice status "Payed"
     * @param invoiceCode - unique order (invoice) code of Invoice
     * @return true if operation success and false if fails
     */
    @Button
    boolean confirmPayment(Long invoiceCode);

    /**
     * Emulates payment operation
     * @param invoiceCode - unique order code of Invoice
     * @return true if operation success and false if fails
     */
    @Button
    boolean payByInvoice(Long invoiceCode);
}

package com.epam.project.dao;

import com.epam.project.domain.Invoice;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import java.util.List;

/**
 * CRUD opeations interface for Invoice entity
 */
public interface IInvoiceDao {

    /**
     * Finds all invoices in database
     * @return List of invoices
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Invoice> findAllInvoices() throws DataNotFoundException;

    /**
     * Finds all new invoices in database
     * @return List of new invoices
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Invoice> findAllNewInvoices() throws DataNotFoundException;

    /**
     * Finds all closed (finished) invoices in database
     * @return List of closed (finished) invoices
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Invoice> findAllFinishedInvoices() throws DataNotFoundException;

    /**
     * Finds all cancelled invoices in database
     * @return List of cancelled invoices
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Invoice> findAllCancelledInvoices() throws DataNotFoundException;

    /**
     * Finds all invoices by User Name
     * @param userName - User Name
     * @return List of all invoices by User
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Invoice> findAllInvoicesByUser(String userName) throws DataNotFoundException;

    /**
     * Finds invoice by order number
     * @param orderNum - order number
     * @return invoice by order number
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Invoice findInvoiceByOrderNumber(Long orderNum) throws DataNotFoundException;

    /**
     * Adds new invoice to database
     * @param invoice - invoice to add in DB
     * @return true if operation success and false if fails
     */
    boolean addInvoiceToDB(Invoice invoice);

    /**
     * Updates existent invoice in DB
     * @param invoice - invoice to update in DB
     * @return true if operation success and false if fails
     */
    boolean updateInvoiceInDB(Invoice invoice);

    /**
     * Deletes existnt invoice from DB
     * @param invoice - invoice to delete from DB
     * @return true if operation success and false if fails
     */
    boolean deleteInvoiceFromDB(Invoice invoice);
}

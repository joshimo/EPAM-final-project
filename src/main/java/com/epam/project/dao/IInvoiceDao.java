package com.epam.project.dao;

import com.epam.project.domain.Invoice;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import java.util.List;

public interface IInvoiceDao {

    List<Invoice> findNewInvoices() throws IncorrectPropertyException, DataBaseConnectionException,
            DataNotFoundException;

    List<Invoice> findAllInvoices() throws IncorrectPropertyException, DataBaseConnectionException,
            DataNotFoundException;

    List<Invoice> findAllInvoicesByUser(String userName) throws IncorrectPropertyException, DataBaseConnectionException,
            DataNotFoundException;

    Invoice findInvoiceByOrderNumber(Long orderNum) throws IncorrectPropertyException, DataBaseConnectionException,
            DataNotFoundException;

    boolean addInvoiceToDB(Invoice invoice) throws IncorrectPropertyException, DataBaseConnectionException;

    boolean updateInvoiceInDB(Invoice invoice) throws IncorrectPropertyException, DataBaseConnectionException;

    boolean deleteInvoiceFromDB(Invoice invoice) throws IncorrectPropertyException, DataBaseConnectionException;
}

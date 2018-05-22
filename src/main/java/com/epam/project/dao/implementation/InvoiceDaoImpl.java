package com.epam.project.dao.implementation;
import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.IInvoiceDao;
import com.epam.project.dao.Mapper;
import com.epam.project.domain.*;
import com.epam.project.exceptions.DataNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class InvoiceDaoImpl extends GenericAbstractDao<Invoice> implements IInvoiceDao {

    private Connection connection;
    private static String SQL_select_base = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id ";
    private static String SQL_selectAll = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id;";
    private static String SQL_selectAllByStatus = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE invoices.status_id=?;";
    private static String SQL_selectAllByUserName = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE user_name=?;";
    private static String SQL_selectByCode = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE invoice_code=?;";
    private static String SQL_addNew = "INSERT INTO project.invoices (invoice_code, user_name, is_paid, status_id, invoice_notes)" +
            " VALUES (?,?,?,?,?);";
    private static String SQL_update = "UPDATE project.invoices SET invoice_code=?, user_name=?, is_paid=?, status_id=?, invoice_notes=? " +
            "WHERE invoice_code=?;";
    private static String SQL_deleteByCode = "DELETE FROM project.invoices WHERE invoice_code=?;";

    private Mapper<Invoice, PreparedStatement> mapperToDB = (Invoice invoice, PreparedStatement preparedStatement) -> {
        preparedStatement.setLong(1, invoice.getInvoiceCode());
        preparedStatement.setString(2, invoice.getUserName());
        preparedStatement.setBoolean(3, invoice.getPaid());
        preparedStatement.setInt(4, invoice.getStatus().ordinal());
        preparedStatement.setString(5, invoice.getInvoiceNotes());
    };

    private Mapper<ResultSet, Invoice> mapperFromDB = (ResultSet resultSet, Invoice invoice) -> {
        invoice.setInvoiceId(resultSet.getInt("invoice_id"));
        invoice.setInvoiceCode(resultSet.getLong("invoice_code"));
        invoice.setUserName(resultSet.getString("user_name"));
        invoice.setPaid(resultSet.getBoolean("is_paid"));
        invoice.setStatus(InvoiceStatus.valueOf(resultSet.getString("status_description")));
        invoice.setDate(resultSet.getTimestamp("invoice_date"));
        invoice.setInvoiceNotes(resultSet.getString("invoice_notes"));
    };

    public InvoiceDaoImpl(Connection connection) {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        this.connection = connection;
    }

    @Override
    public Integer calculateInvoiceNumber() throws DataNotFoundException {
        return calculateRowCounts(connection, "invoices");
    }

    @Override
    public List<Invoice> findAllInvoices() throws DataNotFoundException {
        return findAll(connection, Invoice.class, SQL_selectAll);
    }

    @Override
    public List<Invoice> findInvoices(Integer first, Integer offset) throws DataNotFoundException {
        return findAllFromTo(connection, Invoice.class, first, offset, SQL_select_base);
    }

    @Override
    public List<Invoice> findAllNewInvoices() throws DataNotFoundException {
        return findAsListBy(connection, Invoice.class, SQL_selectAllByStatus, 0);
    }

    @Override
    public List<Invoice> findAllFinishedInvoices() throws DataNotFoundException {
        return findAsListBy(connection, Invoice.class, SQL_selectAllByStatus, 1);
    }

    @Override
    public List<Invoice> findAllCancelledInvoices() throws DataNotFoundException {
        return findAsListBy(connection, Invoice.class, SQL_selectAllByStatus, 2);
    }

    @Override
    public List<Invoice> findAllInvoicesByUser(String userName) throws DataNotFoundException {
        return findAsListBy(connection, Invoice.class, SQL_selectAllByUserName, userName);
    }

    @Override
    public Invoice findInvoiceByOrderNumber(Long orderCode) throws DataNotFoundException {
        return findBy(connection, Invoice.class, SQL_selectByCode, orderCode);
    }

    @Override
    public boolean addInvoiceToDB(Invoice invoice) {
        return addToDB(connection, invoice, SQL_addNew);
    }

    @Override
    public boolean updateInvoiceInDB(Invoice invoice) {
        return updateInDB(connection, invoice, SQL_update, 6, invoice.getInvoiceCode());
    }

    @Override
    public boolean deleteInvoiceFromDB(Invoice invoice) {
        return deleteFromDB(connection, SQL_deleteByCode, invoice.getInvoiceCode());
    }
}
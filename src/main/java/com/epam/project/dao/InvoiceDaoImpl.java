package com.epam.project.dao;

import com.epam.project.entities.*;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InvoiceDaoImpl extends GenericAbstractDao<Invoice> implements IInvoiceDao {

    private static IProductDao productDao;
    private static IPaymentDao paymentDao;

    private static String SQL_selectAll = "SELECT * FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id;";
    private static String SQL_selectAllByUserName = "SELECT * FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id WHERE user_name=?;";
    private static String SQL_selectById = "SELECT * FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id WHERE order_id=?;";
    private static String SQL_addNew = "INSERT INTO project.orders (order_code, user_name, status_id, order_notes)" +
            " VALUES (?,?,?,?);";
    private static String SQL_update = "UPDATE project.orders SET order_code=?, user_name=?, status_id=?, order_notes=? " +
            "WHERE order_id=?;";
    private static String SQL_deleteById = "DELETE FROM project.orders WHERE order_id=?;";

    private Mapper<Invoice, PreparedStatement> mapperToDB = (Invoice invoice, PreparedStatement preparedStatement) -> {
        preparedStatement.setLong(1, invoice.getOrderCode());
        preparedStatement.setString(2, invoice.getUserName());
        preparedStatement.setInt(3, invoice.getStatus().ordinal() + 1);
        preparedStatement.setString(4, invoice.getOrderNotes());
    };

    private Mapper<ResultSet, Invoice> mapperFromDB = (ResultSet resultSet, Invoice invoice) -> {
        invoice.setOrderId(resultSet.getInt("order_id"));
        invoice.setOrderCode(resultSet.getLong("order_code"));
        invoice.setUserName(resultSet.getString("user_name"));
        invoice.setStatus(OrderStatus.valueOf(resultSet.getString("status_description")));
        invoice.setDate(resultSet.getTimestamp("order_date"));
        invoice.setOrderNotes(resultSet.getString("order_notes"));
    };

    public InvoiceDaoImpl() {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        productDao = new ProductDaoImpl();
    }

    @Override
    public List<Invoice> findAllInvoices() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Connection connection = MySQLDaoFactory.getConnection();
        productDao = new ProductDaoImpl();
        paymentDao = new PaymentDaoImpl(connection);
        List<Invoice> invoices = findAll(connection, Invoice.class, SQL_selectAll);
        for (Invoice invoice : invoices) {
            List<Payment> payments = paymentDao.findAllPaymentsByOrderCode(invoice.getOrderCode());
            for (Payment payment : payments) {
                String productCode = payment.getProductCode();
                Product product = productDao.findProductByCode(productCode);
                invoice.addPayment(productCode, payment);
                invoice.addProduct(productCode, product);
            }
        }
        MySQLDaoFactory.closeConnection(connection);
        return invoices;
    }

    @Override
    public List<Invoice> findAllInvoicesByUser(String userName) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Connection connection = MySQLDaoFactory.getConnection();
        productDao = new ProductDaoImpl();
        paymentDao = new PaymentDaoImpl(connection);
        List<Invoice> invoices = findAsListBy(connection, Invoice.class, SQL_selectAllByUserName, userName);
        for (Invoice invoice : invoices) {
            List<Payment> payments = paymentDao.findAllPaymentsByOrderCode(invoice.getOrderCode());
            for (Payment payment : payments) {
                String productCode = payment.getProductCode();
                Product product = productDao.findProductByCode(productCode);
                invoice.addPayment(productCode, payment);
                invoice.addProduct(productCode, product);
            }
        }
        MySQLDaoFactory.closeConnection(connection);
        return invoices;
    }

    @Override
    public boolean addInvoiceToDB(Invoice invoice) throws IncorrectPropertyException, DataBaseConnectionException {
        Set<String> productCodes = invoice.getPayments().keySet();
        Connection connection = MySQLDaoFactory.getConnection();
        try {
            connection.setAutoCommit(false);
            paymentDao = new PaymentDaoImpl(connection);
            addToDB(connection, invoice, SQL_addNew);
            for (String productCode : productCodes)
                paymentDao.addPaymentToDB(invoice.getPayments().get(productCode));
            connection.commit();
            connection.setAutoCommit(true);
            MySQLDaoFactory.closeConnection(connection);
            return true;
        } catch (SQLException sqle) {
            return false;
        }
    }

    @Override
    public boolean updateInvoiceInDB(Invoice invoice) throws IncorrectPropertyException, DataBaseConnectionException {
        return false;
    }

    @Override
    public boolean deleteInvoiceFromDB(Invoice invoice) throws IncorrectPropertyException, DataBaseConnectionException {
        return false;
    }
}

package com.epam.project.dao.implementation;

import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.IPaymentDao;
import com.epam.project.dao.Mapper;
import com.epam.project.domain.InvoiceStatus;
import com.epam.project.domain.Payment;
import com.epam.project.exceptions.DataNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PaymentDaoImpl extends GenericAbstractDao<Payment> implements IPaymentDao {

    private Connection connection;
    private static String SQL_select_base = "SELECT * FROM payments " +
            "JOIN invoice_status ON payments.status_id=invoice_status.status_id ";
    private static String SQL_selectAll = "SELECT * FROM payments " +
            "JOIN invoice_status ON payments.status_id=invoice_status.status_id;";
    private static String SQL_selectByOrderCode = "SELECT * FROM payments " +
            "JOIN invoice_status ON payments.status_id=invoice_status.status_id WHERE invoice_code=?;";
    private static String SQL_selectById = "SELECT * FROM payments " +
            "JOIN invoice_status ON payments.status_id=invoice_status.status_id WHERE payment_id=?;";
    private static String SQL_addNewPayment = "INSERT INTO project.payments (invoice_code, product_code, quantity," +
            "payment_value, status_id, payment_notes) VALUES (?,?,?,?,?,?);";
    private static String SQL_updatePayment = "UPDATE project.payments SET invoice_code=?, product_code=?, quantity=?," +
            "payment_value=?, status_id=?, payment_notes=? WHERE payment_id=?;";
    private static String SQL_deletePaymentById = "DELETE FROM project.payments WHERE payment_id=?;";

    private Mapper<Payment, PreparedStatement> mapperToDB = (Payment payment, PreparedStatement preparedStatement) -> {
        preparedStatement.setLong(1, payment.getOrderCode());
        preparedStatement.setString(2, payment.getProductCode());
        preparedStatement.setDouble(3, payment.getQuantity());
        preparedStatement.setDouble(4, payment.getPaymentValue());
        preparedStatement.setInt(5, payment.getStatusId().ordinal());
        preparedStatement.setString(6, payment.getPaymentNotes());
    };

    private Mapper<ResultSet, Payment> mapperFromDB = (ResultSet resultSet, Payment payment) -> {
        payment.setPaymentId(resultSet.getInt("payment_id"));
        payment.setOrderCode(resultSet.getLong("invoice_code"));
        payment.setProductCode(resultSet.getString("product_code"));
        payment.setQuantity(resultSet.getDouble("quantity"));
        payment.setPaymentValue(resultSet.getDouble("payment_value"));
        payment.setStatusId(InvoiceStatus.valueOf(resultSet.getString("status_description")));
        payment.setPaymentNotes(resultSet.getString("payment_notes"));
    };

    public PaymentDaoImpl(Connection connection) {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        this.connection = connection;
    }

    @Override
    public Integer calculatePaymentsNumber() throws DataNotFoundException {
        return calculateRowCounts(connection, "payments");
    }

    @Override
    public List<Payment> findAllPayments() throws DataNotFoundException {
        return findAll(this.connection, Payment.class, SQL_selectAll);
    }

    @Override
    public List<Payment> findAllPaymentsByOrderCode(Long orderCode) throws DataNotFoundException {
        return findAsListBy(this.connection, Payment.class, SQL_selectByOrderCode, orderCode);
    }

    @Override
    public Payment findPaymentById(Integer id) throws DataNotFoundException {
        return findBy(this.connection, Payment.class, SQL_selectById, id);
    }

    @Override
    public boolean addPaymentToDB(Payment payment) {
        return addToDB(this.connection, payment, SQL_addNewPayment);
    }

    @Override
    public boolean updatePaymentInDB(Payment payment) {
        return updateInDB(connection, payment, SQL_updatePayment, 7, payment.getPaymentId());
    }

    @Override
    public boolean deletePaymentFromDB(Payment payment) {
        return deleteFromDB(connection, SQL_deletePaymentById, payment.getPaymentId());
    }
}
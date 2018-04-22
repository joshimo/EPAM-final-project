package com.epam.project.dao;
import com.epam.project.entities.OrderStatus;
import com.epam.project.entities.Payment;
import com.epam.project.exceptions.DataNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Deprecated
public class PaymentDaoImplementation implements IPaymentDao {

    private Connection connection;
    private static String SQL_selectAll = "SELECT * FROM payments " +
            "JOIN order_status ON payments.status_id=order_status.status_id;";
    private static String SQL_selectByOrderCode = "SELECT * FROM payments " +
            "JOIN order_status ON payments.status_id=order_status.status_id WHERE order_code=?;";
    private static String SQL_selectById = "SELECT * FROM payments " +
            "JOIN order_status ON payments.status_id=order_status.status_id WHERE payment_id=?;";

    private Mapper<Payment, PreparedStatement> mapperToDB = (Payment payment, PreparedStatement preparedStatement) -> {
    };

    private Mapper<ResultSet, Payment> mapperFromDB = (ResultSet resultSet, Payment payment) -> {
        payment.setPaymentId(resultSet.getInt("payment_id"));
        payment.setOrderCode(resultSet.getLong("order_code"));
        payment.setProductCode(resultSet.getString("product_code"));
        payment.setQuantity(resultSet.getDouble("quantity"));
        payment.setPaymentValue(resultSet.getDouble("payment_value"));
        payment.setStatusId(OrderStatus.valueOf(resultSet.getString("status_description")));
        payment.setPaymentNotes(resultSet.getString("payment_notes"));
    };

    public PaymentDaoImplementation(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Payment> findAllPayments() throws DataNotFoundException {
        List<Payment> payments = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                mapperFromDB.map(resultSet, payment);
                payments.add(payment);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return payments;
    }

    @Override
    public List<Payment> findAllPaymentsByOrderCode(Long orderCode) throws DataNotFoundException {
        List<Payment> payments = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByOrderCode);
            preparedStatement.setLong(1, orderCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                mapperFromDB.map(resultSet, payment);
                payments.add(payment);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return payments;
    }

    @Override
    public Payment findPaymentById(Integer id) throws DataNotFoundException {
        Payment payment = new Payment();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                mapperFromDB.map(resultSet, payment);
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return payment;
    }

    @Override
    public <T extends Exception> boolean addPaymentToDB(Payment payment) throws T {
        return false;
    }

    @Override
    public <T extends Exception> boolean updatePaymentInDB(Payment payment) throws T {
        return false;
    }

    @Override
    public <T extends Exception> boolean deletePaymentFromDB(Payment payment) throws T {
        return false;
    }
}

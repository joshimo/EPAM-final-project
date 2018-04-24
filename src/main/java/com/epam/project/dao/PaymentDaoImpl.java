package com.epam.project.dao;

import com.epam.project.entities.OrderStatus;
import com.epam.project.entities.Payment;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class PaymentDaoImpl extends GenericAbstractDao<Payment> implements IPaymentDao {

    private Connection connection;
    private static String SQL_selectAll = "SELECT * FROM payments " +
            "JOIN order_status ON payments.status_id=order_status.status_id;";
    private static String SQL_selectByOrderCode = "SELECT * FROM payments " +
            "JOIN order_status ON payments.status_id=order_status.status_id WHERE order_code=?;";
    private static String SQL_selectById = "SELECT * FROM payments " +
            "JOIN order_status ON payments.status_id=order_status.status_id WHERE payment_id=?;";
    private static String SQL_addNewPayment = "INSERT INTO project.payments (order_code, product_code, quantity," +
            "payment_value, status_id, payment_notes) VALUES (?,?,?,?,?,?);";
    private static String SQL_updatePayment = "UPDATE project.payments SET order_code=?, product_code=?, quantity=?," +
            "payment_value=?, status_id=?, payment_notes=? WHERE payment_id=?;";
    private static String SQL_deletePaymentById = "DELETE FROM project.payments WHERE payment_id=?;";

    private Mapper<Payment, PreparedStatement> mapperToDB = (Payment payment, PreparedStatement preparedStatement) -> {
        preparedStatement.setLong(1, payment.getOrderCode());
        preparedStatement.setString(2, payment.getProductCode());
        preparedStatement.setDouble(3, payment.getQuantity());
        preparedStatement.setDouble(4, payment.getPaymentValue());
        preparedStatement.setInt(5, payment.getStatusId().ordinal() + 1);
        preparedStatement.setString(6, payment.getPaymentNotes());
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

    public PaymentDaoImpl(Connection connection) {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        this.connection = connection;
    }

    @Override
    public List<Payment> findAllPayments()
            throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        List<Payment> payments = findAll(this.connection, Payment.class, SQL_selectAll);
        return payments;
    }

    @Override
    public List<Payment> findAllPaymentsByOrderCode(Long orderCode)
            throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        List<Payment> results = findAsListBy(this.connection, Payment.class, SQL_selectByOrderCode, orderCode);
        return results;
    }

    @Override
    public Payment findPaymentById(Integer id)
            throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Payment payment = findBy(this.connection, Payment.class, SQL_selectById, id);
        return payment;
    }

    @Override
    public boolean addPaymentToDB(Payment payment)
            throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        return addToDB(this.connection, payment, SQL_addNewPayment);
    }

    @Override
    public boolean updatePaymentInDB(Payment payment)
            throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        return updateInDB(connection, payment, SQL_updatePayment, 7, payment.getPaymentId());
    }

    @Override
    public boolean deletePaymentFromDB(Payment payment)
            throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        return deleteFromDB(connection, SQL_deletePaymentById, payment.getPaymentId());
    }
}

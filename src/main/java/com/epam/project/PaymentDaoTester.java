package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.OrderStatus;
import com.epam.project.entities.Payment;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.util.List;

public class PaymentDaoTester {
    private static IPaymentDao paymentDao;
    private static List<Payment> payments;

    public PaymentDaoTester() throws DataBaseNotSupportedException, IncorrectPropertyException {
        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
    }

    public static void main(String... args) throws Exception {
        PaymentDaoTester paymentDaoTester = new PaymentDaoTester();
        paymentDaoTester.testPaymentDao();
    }

    public void testPaymentDao() throws DataBaseConnectionException {
        Connection connection = MySQLDaoFactory.getConnection();
        paymentDao = new PaymentDaoImpl(connection);

        Payment payment = new Payment();
        payment.setPaymentId(10);
        payment.setOrderCode(1L);
        payment.setProductCode("C001");
        payment.setQuantity(0.4);
        payment.setPaymentValue(0.01);
        payment.setStatusId(OrderStatus.CREATED);
        payment.setPaymentNotes("Added by PaymentDaoTester runner");
        //System.out.println(paymentDao.addPaymentToDB(payment));
        //System.out.println(paymentDao.updatePaymentInDB(payment));
        //System.out.println(paymentDao.deletePaymentFromDB(payment));
        System.out.println("All payments\n");
        payments = paymentDao.findAllPayments();
        System.out.println(payments);
        System.out.println("\nPayments by order code 999:");
        payments = paymentDao.findAllPaymentsByOrderCode(1L);
        System.out.println(payments);
        System.out.println("\nPayments by id 1:");
        System.out.println(paymentDao.findPaymentById(1));
    }
}
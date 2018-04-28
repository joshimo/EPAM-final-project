package com.epam.project.dao;

import com.epam.project.domain.Payment;
import com.epam.project.exceptions.DataNotFoundException;

import java.util.List;

public interface IPaymentDao {

    List<Payment> findAllPayments() throws DataNotFoundException;

    List<Payment> findAllPaymentsByOrderCode(Long orderCode) throws DataNotFoundException;

    Payment findPaymentById(Integer id) throws DataNotFoundException;

    boolean addPaymentToDB(Payment payment);

    boolean updatePaymentInDB(Payment payment);

    boolean deletePaymentFromDB(Payment payment);
}

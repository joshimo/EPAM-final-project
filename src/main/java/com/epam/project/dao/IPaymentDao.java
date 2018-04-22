package com.epam.project.dao;

import com.epam.project.entities.Payment;

import java.util.List;

public interface IPaymentDao {

    <T extends Exception> List<Payment> findAllPayments() throws T;

    <T extends Exception> List<Payment> findAllPaymentsByOrderCode(Long orderCode) throws T;

    <T extends Exception> Payment findPaymentById(Integer id) throws T;

    <T extends Exception> boolean addPaymentToDB(Payment payment) throws T;

    <T extends Exception> boolean updatePaymentInDB(Payment payment) throws T;

    <T extends Exception> boolean deletePaymentFromDB(Payment payment) throws T;
}

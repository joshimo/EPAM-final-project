package com.epam.project.dao;

import com.epam.project.domain.Payment;
import com.epam.project.exceptions.DataNotFoundException;

import java.util.List;

/**
 * CRUD opeations interface for Payment entity
 */
public interface IPaymentDao {

    /**
     * Calculates total payments number available in DB
     * @return count of payments in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculatePaymentsNumber() throws DataNotFoundException;

    /**
     * Finds all payments in DB
     * @return List of all payments
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Payment> findAllPayments() throws DataNotFoundException;

    /**
     * Finds all payments in DB by invoice code
     * @param orderCode - invoice code
     * @return List of all payments by invoice code
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Payment> findAllPaymentsByOrderCode(Long orderCode) throws DataNotFoundException;

    /**
     * Finds payment in DB by id number
     * @param id - payment id number
     * @return payment
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Payment findPaymentById(Integer id) throws DataNotFoundException;

    /**
     * Adds new payment to DB
     * @param payment - payment to add in DB
     * @return true if operation success and false if fails
     */
    boolean addPaymentToDB(Payment payment);

    /**
     * Updates existent payment in DB
     * @param payment - payment to update in DB
     * @return true if operation success and false if fails
     */
    boolean updatePaymentInDB(Payment payment);

    /**
     * Deletes existent payment from DB
     * @param payment - payment to delete from DB
     * @return true if operation success and false if fails
     */
    boolean deletePaymentFromDB(Payment payment);
}

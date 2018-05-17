package com.epam.project.dao;

import com.epam.project.domain.Transaction;
import com.epam.project.domain.TransactionType;
import com.epam.project.exceptions.DataNotFoundException;

import java.util.List;

/**
 * CRUD operations interface for Transaction entity
 */
public interface ITransactionDao {

    /**
     * Calculates total transactions number available in DB
     * @return count of transactions in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateTransactionsNumber() throws DataNotFoundException;

    /**
     * Finds all transactions in DB
     * @return List of all transactions
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Transaction> findAllTransactions() throws DataNotFoundException;

    /**
     * Finds transactions in DB from
     * @param first first row number
     * @param offset offset
     * @return List transactions
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Transaction> findTransactions(Integer first, Integer offset) throws DataNotFoundException;

    /**
     * Finds all transactions in DB by invoice number
     * @param invoiceCode - invoice number
     * @return List of transactions by invoice number
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Transaction> findAllTransactionsByInvoice(Long invoiceCode) throws DataNotFoundException;

    /**
     * Finds all transactions in DB made by User
     * @param userName - User name
     * @return List of transactions by User
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Transaction> findAllTransactionsByUser(String userName) throws DataNotFoundException;

    /**
     * Finds all transactions in DB by transaction type (PAYMENT or REFUND)
     * @param type - transaction type (PAYMENT or REFUND)
     * @return List of transactions by transaction type
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Transaction> findAllTransactionsByType(TransactionType type) throws DataNotFoundException;

    /**
     * Finds all transactions by transaction id number
     * @param id - transaction id number
     * @return transaction by id number
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Transaction findTransactionById(Integer id) throws DataNotFoundException;

    /**
     * Adds new transaction in DB
     * @param transaction - transaction to add in DB
     * @return true if operation success and false if fails
     */
    boolean addTransactionToDB(Transaction transaction);
}
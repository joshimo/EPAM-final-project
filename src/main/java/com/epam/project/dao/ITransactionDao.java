package com.epam.project.dao;

import com.epam.project.domain.Transaction;
import com.epam.project.domain.TransactionType;
import com.epam.project.exceptions.DataNotFoundException;

import java.util.List;

public interface ITransactionDao {

    List<Transaction> findAllTransactions() throws DataNotFoundException;

    List<Transaction> findAllTransactionsByInvoice(Long invoiceCode) throws DataNotFoundException;

    List<Transaction> findAllTransactionsByUser(String userName) throws DataNotFoundException;

    List<Transaction> findAllTransactionsByType(TransactionType type) throws DataNotFoundException;

    Transaction findTransactionById(Integer id) throws DataNotFoundException;

    boolean addTransactionToDB(Transaction transaction);
}

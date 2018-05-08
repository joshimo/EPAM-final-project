package com.epam.project.service;

import com.epam.project.domain.Transaction;
import com.epam.project.exceptions.TransactionServiceException;

import java.util.List;

public interface ITransactionServ {

    List<Transaction> findAllTransactions() throws TransactionServiceException;

    List<Transaction> findAllTransactionsByInvoice(Long invoiceCode) throws TransactionServiceException;

    List<Transaction> findAllTransactionsByUser(String userName) throws TransactionServiceException;

    boolean addTransaction(Transaction transaction);
}

package com.epam.project.service.implementation;

import com.epam.project.dao.*;
import com.epam.project.domain.Payment;
import com.epam.project.domain.Transaction;
import com.epam.project.domain.TransactionType;
import com.epam.project.exceptions.*;
import com.epam.project.service.Button;
import com.epam.project.service.ITransactionServ;
import org.apache.log4j.Logger;
import java.util.LinkedList;
import java.util.List;

public class TransactionService implements ITransactionServ {

    private static final Logger log = Logger.getLogger(TransactionService.class);
    private static final DataBaseSelector source = DataBaseSelector.MY_SQL;
    private static DaoFactory daoFactory;
    private static ITransactionDao transactionDao;

    static {
        try {
            daoFactory = DaoFactory.getDaoFactory(source);
            transactionDao = daoFactory.getTransactionDao();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
        }
    }

    /** Service special methods */

    public Transaction createTransactionFromPayment(Payment payment, String userName, TransactionType type) {
        return createTransactionFromPayment(payment, userName, type, "");
    }

    public Transaction createTransactionFromPayment(Payment payment, String userName, TransactionType type, String notes) {
        Transaction transaction = new Transaction();
        transaction.setPaymentId(payment.getPaymentId());
        transaction.setInvoiceCode(payment.getOrderCode());
        transaction.setUserName(userName);
        transaction.setTransactionType(type);
        transaction.setPaymentValue(payment.getPaymentValue());
        transaction.setNotes(notes);
        return transaction;
    }

    /** CRUD methods */
    @Button
    public List<Transaction> findAllTransactions() throws TransactionServiceException {
        List<Transaction> transactions = new LinkedList<>();
        try {
            daoFactory.open();
            transactionDao = daoFactory.getTransactionDao();
            transactions = transactionDao.findAllTransactions();
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new TransactionServiceException();
        }
        return transactions;
    }

    @Button
    public List<Transaction> findAllTransactionsByInvoice(Long invoiceCode) throws TransactionServiceException {
        List<Transaction> transactions = new LinkedList<>();
        try {
            daoFactory.open();
            transactionDao = daoFactory.getTransactionDao();
            transactions = transactionDao.findAllTransactionsByInvoice(invoiceCode);
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new TransactionServiceException();
        }
        return transactions;
    }

    @Button
    public List<Transaction> findAllTransactionsByUser(String userName) throws TransactionServiceException {
        List<Transaction> transactions = new LinkedList<>();
        try {
            daoFactory.open();
            transactionDao = daoFactory.getTransactionDao();
            transactions = transactionDao.findAllTransactionsByUser(userName);
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new TransactionServiceException();
        }
        return transactions;
    }

    @Button
    public List<Transaction> findAllTransactionsByType(TransactionType type) throws TransactionServiceException {
        List<Transaction> transactions = new LinkedList<>();
        try {
            daoFactory.open();
            transactionDao = daoFactory.getTransactionDao();
            transactions = transactionDao.findAllTransactionsByType(type);
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new TransactionServiceException();
        }
        return transactions;
    }

    public Transaction findTransactionById(Integer id) throws TransactionServiceException {
        Transaction transaction = new Transaction();
        try {
            daoFactory.open();
            transactionDao = daoFactory.getTransactionDao();
            transaction = transactionDao.findTransactionById(id);
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new TransactionServiceException();
        }
        return transaction;
    }

    public boolean addTransaction(Transaction transaction) {
        try {
            daoFactory.open();
            transactionDao = daoFactory.getTransactionDao();
            boolean result = transactionDao.addTransactionToDB(transaction);
            daoFactory.close();
            return result;
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
    }
}
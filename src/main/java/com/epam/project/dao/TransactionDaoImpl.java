package com.epam.project.dao;

import com.epam.project.domain.Transaction;
import com.epam.project.exceptions.DataNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TransactionDaoImpl extends GenericAbstractDao<Transaction> implements ITransactionDao {

    private Connection connection;
    private static String SQL_selectAll = "SELECT * FROM transactions";
    private static String SQL_selectByInvoice = "SELECT * FROM transactions WHERE invoice_code=?;";
    private static String SQL_selectByUserName = "SELECT * FROM transactions WHERE user_name=?;";
    private static String SQL_selectById = "SELECT * FROM transactions WHERE transaction_id=?;";
    private static String SQL_addNew = "INSERT INTO project.transactions " +
            "(payment_id, invoice_code, user_name, payment_value, transaction_notes) " +
            "VALUES (?, ?, ?, ?, ?);";


    private Mapper<Transaction, PreparedStatement> mapperToDB = (Transaction transaction, PreparedStatement preparedStatement) -> {
        preparedStatement.setInt(1, transaction.getPaymentId());
        preparedStatement.setLong(2, transaction.getInvoiceCode());
        preparedStatement.setString(3, transaction.getUserName());
        preparedStatement.setDouble(4, transaction.getPaymentValue());
        preparedStatement.setString(5, transaction.getNotes());
    };

    private Mapper<ResultSet, Transaction> mapperFromDB = (ResultSet resultSet, Transaction transaction) -> {
        transaction.setTransactionId(resultSet.getInt("transaction_id"));
        transaction.setPaymentId(resultSet.getInt("payment_id"));
        transaction.setInvoiceCode(resultSet.getLong("invoice_code"));
        transaction.setUserName(resultSet.getString("user_name"));
        transaction.setPaymentValue(resultSet.getDouble("payment_value"));
        transaction.setNotes(resultSet.getString("transaction_notes"));
    };

    public TransactionDaoImpl(Connection connection) {
        this.connection = connection;
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public List<Transaction> findAllTransactions() throws DataNotFoundException {
        return findAll(this.connection, Transaction.class, SQL_selectAll);
    }

    @Override
    public List<Transaction> findAllTransactionsByInvoice(Long invoiceCode) throws DataNotFoundException {
        return findAsListBy(this.connection, Transaction.class, SQL_selectByInvoice, invoiceCode);
    }

    @Override
    public List<Transaction> findAllTransactionsByUser(String userName) throws DataNotFoundException {
        return findAsListBy(this.connection, Transaction.class, SQL_selectByUserName, userName);
    }

    @Override
    public Transaction findTransactionById(Integer id) throws DataNotFoundException {
        return findBy(this.connection, Transaction.class, SQL_selectById, id);
    }

    @Override
    public boolean addTransactionToDB(Transaction transaction) {
        return addToDB(this.connection, transaction, SQL_addNew);
    }
}

package com.epam.project.dao;

import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.log4j.Logger;

public abstract class DaoFactory {

    /** DAO Factory methods */
    public abstract IUserDao getUserDao();
    public abstract IProductDao getProductDao();
    public abstract IInvoiceDao getInvoiceDao();
    public abstract IPaymentDao getPaymentDao();
    public abstract ITransactionDao getTransactionDao();

    private static final Logger log = Logger.getLogger(DaoFactory.class);

    /** Connection open and closing methods */
    abstract void closeConnection() throws DataBaseConnectionException;
    public abstract void open() throws DataBaseConnectionException;
    public abstract void close();

    /** Transaction methods */
    public abstract void beginTransaction() throws DataBaseConnectionException;
    public abstract void commitTransaction() throws DataBaseConnectionException;
    public abstract void rollbackTransaction() throws DataBaseConnectionException;

    public static DaoFactory getDaoFactory(DataBaseSelector dataBase) throws
            DataBaseNotSupportedException,
            IncorrectPropertyException,
            DataBaseConnectionException {
        switch (dataBase) {
            case MY_SQL:
                return new MySQLDaoFactory();
            case MS_SQL:
                log.error("Database " + dataBase + " not supported yet");
                throw new DataBaseNotSupportedException(dataBase);
            case ORACLE:
                log.error("Database " + dataBase + " not supported yet");
                throw new DataBaseNotSupportedException(dataBase);
            case POSTGRESS:
                log.error("Database " + dataBase + " not supported yet");
                throw new DataBaseNotSupportedException(dataBase);
            default:
                log.error("Database type not set");
                throw new DataBaseNotSupportedException("Database type not set");
        }
    }
}
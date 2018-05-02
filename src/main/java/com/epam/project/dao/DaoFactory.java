package com.epam.project.dao;

import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.log4j.Logger;

public abstract class DaoFactory {

    public abstract IUserDao getUserDao();
    public abstract IProductDao getProductDao();
    public abstract IInvoiceDao getInvoiceDao();

    private static final Logger log = Logger.getLogger(DaoFactory.class);

    /** Connection closing method */
    public abstract void closeConnection() throws DataBaseConnectionException;

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

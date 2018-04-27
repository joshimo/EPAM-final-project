package com.epam.project.dao;

import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.log4j.Logger;

public abstract class DaoFactory {

    public abstract IUserDao getUserDao();
    public abstract IProductDao getProductDao();
    public abstract IInvoiceDao getInvoiceDao();

    private static final Logger log = Logger.getLogger(DaoFactory.class);

    public static DaoFactory getDaoFactory(DataBaseSelector dataBase) throws DataBaseNotSupportedException, IncorrectPropertyException {
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

package com.epam.project.dao;

import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;

public abstract class DaoFactory {

    public abstract IUserDao getUserDao();
    public abstract IProductDao getProductDao();
    public abstract IInvoiceDao getInvoiceDao();

    public static DaoFactory getDaoFactory(DataBaseSelector dataBase) throws DataBaseNotSupportedException, IncorrectPropertyException {
        switch (dataBase) {
            case MY_SQL:
                return new MySQLDaoFactory();
            case MS_SQL:
                throw new DataBaseNotSupportedException(dataBase);
            case ORACLE:
                throw new DataBaseNotSupportedException(dataBase);
            case POSTGRESS:
                throw new DataBaseNotSupportedException(dataBase);
            default:
                throw new DataBaseNotSupportedException("Database type not set");
        }
    }
}

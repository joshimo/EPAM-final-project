package com.epam.project.service.implementation;

import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IPaymentDao;
import com.epam.project.domain.Payment;
import com.epam.project.exceptions.*;
import com.epam.project.service.Button;
import com.epam.project.service.IPaymentServ;
import org.apache.log4j.Logger;

public class PaymentService implements IPaymentServ{

    private static final DataBaseSelector source = DataBaseSelector.MY_SQL;
    private static final Logger log = Logger.getLogger(UserService.class);
    private static DaoFactory daoFactory;
    private static IPaymentDao paymentDao;

    static {
        try {
            daoFactory = DaoFactory.getDaoFactory(source);
            paymentDao = daoFactory.getPaymentDao();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
        }
    }

    private boolean validatePayment(Payment payment) {
        return ((payment.getOrderCode() != null)
                && (payment.getProductCode() != null)
                && (!payment.getProductCode().equals(""))
                && (payment.getQuantity() != null)
                && (payment.getStatusId() != null));
    }

    @Button
    @Override
    public boolean updatePayment(Payment payment) {
        boolean result;
        if (validatePayment(payment))
            try {
                daoFactory.beginTransaction();
                paymentDao = daoFactory.getPaymentDao();
                result = paymentDao.deletePaymentFromDB(payment) && paymentDao.addPaymentToDB(payment);
                daoFactory.commitTransaction();
                return result;
            } catch (DataBaseConnectionException ex) {
                log.error(ex);
                return false;
            }
        return false;
    }

    @Button
    @Override
    public boolean addPayment(Payment payment) {
        boolean result;
        if (validatePayment(payment))
            try {
                daoFactory.beginTransaction();
                paymentDao = daoFactory.getPaymentDao();
                result = paymentDao.addPaymentToDB(payment);
                daoFactory.commitTransaction();
                return result;
            } catch (DataBaseConnectionException ex) {
                log.error(ex);
                return false;
            }
        return false;
    }
}
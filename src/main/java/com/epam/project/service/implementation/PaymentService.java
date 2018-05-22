package com.epam.project.service.implementation;

import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IPaymentDao;
import com.epam.project.dao.IProductDao;
import com.epam.project.domain.Payment;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.*;
import com.epam.project.service.Button;
import com.epam.project.service.IPaymentServ;
import org.apache.log4j.Logger;

import java.util.List;

public class PaymentService implements IPaymentServ{

    private static final DataBaseSelector source = DataBaseSelector.MY_SQL;
    private static final Logger log = Logger.getLogger(UserService.class);
    private static DaoFactory daoFactory;
    private static IPaymentDao paymentDao;
    private static IProductDao productDao;

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
    public synchronized boolean updatePayment(Payment payment) {
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
    public synchronized boolean addPayment(Payment newPayment) {
        if (validatePayment(newPayment))
            try {
                boolean update = false;
                daoFactory.beginTransaction();
                paymentDao = daoFactory.getPaymentDao();
                productDao = daoFactory.getProductDao();
                Product product = productDao.findProductByCode(newPayment.getProductCode());
                List<Payment> payments = paymentDao.findAllPaymentsByOrderCode(newPayment.getOrderCode());
                for (Payment existPayment : payments) {
                    if (existPayment.getProductCode().equals(newPayment.getProductCode())) {
                        update = true;
                        product.setQuantity(product.getQuantity() - newPayment.getQuantity());
                        product.setReservedQuantity(product.getReservedQuantity() + newPayment.getQuantity());
                        newPayment.setPaymentId(existPayment.getPaymentId());
                        newPayment.setQuantity(existPayment.getQuantity() + newPayment.getQuantity());
                        newPayment.setPaymentValue(product.getCost() * newPayment.getQuantity());
                    }
                }
                if (!update) {
                    newPayment.setPaymentValue(product.getCost() * newPayment.getQuantity());
                    product.setQuantity(product.getQuantity() - newPayment.getQuantity());
                    product.setReservedQuantity(product.getReservedQuantity() + newPayment.getQuantity());
                }
                if ((!productDao.updateProductInDB(product))
                        || (!(update ? paymentDao.updatePaymentInDB(newPayment) : paymentDao.addPaymentToDB(newPayment)))) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
                daoFactory.commitTransaction();
                return true;
            } catch (DataBaseConnectionException | DataNotFoundException ex) {
                log.error(ex);
                return false;
            }
        return false;
    }
}
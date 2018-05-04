package com.epam.project.service;

import com.epam.project.dao.*;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.OrderStatus;
import com.epam.project.domain.Payment;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.log4j.Logger;

import java.util.*;

public class InvoiceService {

    private static final Logger log = Logger.getLogger(ProductService.class);
    private static final DataBaseSelector source = DataBaseSelector.MY_SQL;
    private static DaoFactory daoFactory;
    private static IInvoiceDao invoiceDao;
    private static IPaymentDao paymentDao;
    private static IProductDao productDao;

    static {
        try {
            daoFactory = DaoFactory.getDaoFactory(source);
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            productDao = daoFactory.getProductDao();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
        }
    }

    public static List<Invoice> findAllInvoices() {
        List<Invoice> invoices = new LinkedList<>();
        try {
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            productDao = daoFactory.getProductDao();
            invoices = invoiceDao.findAllInvoices();
            for (Invoice invoice : invoices) {
                if (!addPaymentsToInvoice(invoice, paymentDao, productDao))
                    daoFactory.rollbackTransaction();
            }
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
        }
        return invoices;
    }

    public static List<Invoice> findNewInvoices() {
        List<Invoice> invoices = new LinkedList<>();
        try {
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            productDao = daoFactory.getProductDao();
            invoices = invoiceDao.findNewInvoices();
            for (Invoice invoice : invoices) {
                if (!addPaymentsToInvoice(invoice, paymentDao, productDao))
                    daoFactory.rollbackTransaction();
            }
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
        }
        return invoices;
    }

    public static List<Invoice> findInvoicesByUser(String userName) {
        List<Invoice> invoices = new LinkedList<>();
        try {
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            productDao = daoFactory.getProductDao();
            invoices = invoiceDao.findAllInvoicesByUser(userName);
            for (Invoice invoice : invoices) {
                if (!addPaymentsToInvoice(invoice, paymentDao, productDao))
                    daoFactory.rollbackTransaction();
            }
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
        }
        return invoices;
    }

    public static Invoice findInvoiceByOrderNumber(Long orderNum) {
        Invoice invoice = new Invoice();
        try {
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            productDao = daoFactory.getProductDao();
            invoice = invoiceDao.findInvoiceByOrderNumber(orderNum);
            if (!addPaymentsToInvoice(invoice, paymentDao, productDao))
                daoFactory.rollbackTransaction();
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
        }
        return invoice;
    }

    public static boolean addInvoice(Invoice invoice) {
        try {
            if (!validateInvoice(invoice))
                return false;
            Set<String> productCodes = invoice.getPayments().keySet();
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            if (!invoiceDao.addInvoiceToDB(invoice)) {
                daoFactory.rollbackTransaction();
                return false;
            }
            for (String productCode : productCodes)
                if (!paymentDao.addPaymentToDB(invoice.getPayments().get(productCode))) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataBaseConnectionException ex) {
            return false;
        }
        return true;
    }

    public static boolean updateInvoice(Invoice invoice) {
        if (!validateInvoice(invoice))
            return false;
        try {
            Set<String> productCodes = invoice.getPayments().keySet();
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            for (String productCode : productCodes)
                if (!paymentDao.updatePaymentInDB(invoice.getPayments().get(productCode))) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
            if (!invoiceDao.updateInvoiceInDB(invoice)) {
                daoFactory.rollbackTransaction();
                return false;
            }
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataBaseConnectionException ex) {
            return false;
        }
        return true;
    }

    public static boolean deleteInvoice(Invoice invoice) {
        try {
            Set<String> productCodes = invoice.getPayments().keySet();
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            for (String productCode : productCodes)
                if (!paymentDao.deletePaymentFromDB(invoice.getPayments().get(productCode))) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
            if (!invoiceDao.deleteInvoiceFromDB(invoice)) {
                daoFactory.rollbackTransaction();
                return false;
            }
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataBaseConnectionException ex) {
            return false;
        }
        return true;
    }

    /** Private class methods */

    /** Invoice data integrity validator */
    private static boolean validateInvoice(Invoice invoice) {
        if ((invoice.getOrderCode() == null)
            || (invoice.getPayments().size() == 0)
            || (invoice.getProducts().size() == 0)
            || (invoice.getPayments().size() != invoice.getProducts().size()))
            return false;
        Long orderCode = invoice.getOrderCode();
        OrderStatus status = invoice.getStatus();
        for (Map.Entry<String, Payment> paymentEntry : invoice.getPayments().entrySet())
            if (!paymentEntry.getValue().getOrderCode().equals(orderCode)
                    || (paymentEntry.getValue().getStatusId() != status))
                return false;
        return true;
    }

    private static synchronized boolean addPaymentsToInvoice(Invoice invoice, IPaymentDao paymentDao, IProductDao productDao) {
        List<Payment> payments;
        try {
            payments = paymentDao.findAllPaymentsByOrderCode(invoice.getOrderCode());
            if (payments == null)
                return false;
            for (Payment payment : payments) {
                String productCode = payment.getProductCode();
                Product product = productDao.findProductByCode(productCode);
                invoice.addPayment(productCode, payment);
                invoice.addProduct(productCode, product);
            }
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            return false;
        }
        return true;
    }
}

package com.epam.project.service;

import com.epam.project.dao.*;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.OrderStatus;
import com.epam.project.domain.Payment;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.*;
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

    /** CRUD methods */
    @Outer
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

    @Outer
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

    @Outer
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

    @Outer
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

    @Outer
    public static boolean addInvoice(Invoice invoice) {
        try {
            if (!validateInvoice(invoice))
                return false;
            Set<String> productCodes = invoice.getPayments().keySet();
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            productDao = daoFactory.getProductDao();
            if (!invoiceDao.addInvoiceToDB(invoice)) {
                daoFactory.rollbackTransaction();
                return false;
            }
            for (String productCode : productCodes) {
                Product product = productDao.findProductByCode(productCode);
                Payment payment = invoice.getPayments().get(productCode);
                product.setQuantity(product.getQuantity() - payment.getQuantity());
                product.setReservedQuantity(product.getReservedQuantity() + payment.getQuantity());
                if ((!paymentDao.addPaymentToDB(invoice.getPayments().get(productCode)))
                    || (!productDao.updateProductInDB(product))) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
            }
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataNotFoundException | DataBaseConnectionException ex) {
            return false;
        }
        return true;
    }

    // ToDo: add product return reserved by invoice product amount to stock - done
    // ToDo: needs test - done
    @Outer
    public static boolean updateInvoice(Invoice invoice) {
        if (!validateInvoice(invoice))
            return false;
        Invoice oldInvoice = findInvoiceByOrderNumber(invoice.getOrderCode());
        if (!deleteInvoice(oldInvoice))
            return false;
        if (!addInvoice(invoice)) {
            addInvoice(oldInvoice);
            return false;
        }
        return true;
    }

    @Outer
    public static boolean deleteInvoice(Long orderCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        //ToDO: simplify if
        if ((invoice == null) || (invoice.getStatus() != OrderStatus.CREATED))
            return false;
        return deleteInvoice(invoice);
    }

    private static boolean deleteInvoice(Invoice invoice) {
        try {
            Set<String> productCodes = invoice.getPayments().keySet();
            daoFactory.beginTransaction();
            invoiceDao = daoFactory.getInvoiceDao();
            paymentDao = daoFactory.getPaymentDao();
            productDao = daoFactory.getProductDao();
            for (String productCode : productCodes) {
                Product product = productDao.findProductByCode(productCode);
                Payment payment = invoice.getPayments().get(productCode);
                product.setQuantity(product.getQuantity() + payment.getQuantity());
                product.setReservedQuantity(product.getReservedQuantity() - payment.getQuantity());
                if ((!paymentDao.deletePaymentFromDB(payment)) || (!productDao.updateProductInDB(product))) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
            }
            if (!invoiceDao.deleteInvoiceFromDB(invoice)) {
                daoFactory.rollbackTransaction();
                return false;
            }
            daoFactory.commitTransaction();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            return false;
        }
        return true;
    }

    /** Special methods */
    @Outer
    public static boolean removeProductFromInvoice(Long orderCode, String productCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        //ToDO: simplify if
        if ((invoice == null) || (invoice.getStatus() != OrderStatus.CREATED))
            return false;
        return removeProductFromInvoice(invoice, productCode);
    }

    private static boolean removeProductFromInvoice(Invoice invoice, String productCode) {
        if (invoice.getProducts().containsKey(productCode)
                && invoice.getPayments().containsKey(productCode)) {
            Payment payment = invoice.getPayments().get(productCode);
            invoice.getPayments().remove(productCode);
            invoice.getProducts().remove(productCode);
            //ToDO: simplify if
            if (!removePayment(payment))
                return false;
            return true;
        }
        return false;
    }

    @Outer
    public static boolean closeInvoice (Long orderCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        //ToDO: simplify if
        if ((invoice == null) || (invoice.getStatus() != OrderStatus.CREATED))
            return false;
        return closeInvoice(invoice);
    }

    private static boolean closeInvoice (Invoice invoice) {
        Set<String> productCodes = invoice.getPayments().keySet();
        try {
            daoFactory.beginTransaction();
            productDao = daoFactory.getProductDao();
            paymentDao = daoFactory.getPaymentDao();
            invoiceDao = daoFactory.getInvoiceDao();
            for (String productCode : productCodes) {
                Product product = productDao.findProductByCode(productCode);
                Payment payment = paymentDao.findPaymentById(invoice.getPayments().get(productCode).getPaymentId());
                product.setReservedQuantity(product.getReservedQuantity() - payment.getQuantity());
                payment.setStatusId(OrderStatus.FINISHED);
                if ((!productDao.updateProductInDB(product)) || (!paymentDao.updatePaymentInDB(payment))) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
            }
            invoice.setStatus(OrderStatus.FINISHED);
            if (!invoiceDao.updateInvoiceInDB(invoice)) {
                daoFactory.rollbackTransaction();
                return false;
            }
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException | IncorrectPropertyException | DataNotFoundException ex) {
            log.error(ex);
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

    private static boolean removePayment(Payment payment) {
        try {
            daoFactory.beginTransaction();
            paymentDao = daoFactory.getPaymentDao();
            productDao = daoFactory.getProductDao();
            Double quantity = payment.getQuantity();
            Product product = productDao.findProductByCode(payment.getProductCode());
            product.setQuantity(product.getQuantity() + quantity);
            product.setReservedQuantity(product.getReservedQuantity() - quantity);
            if ((!paymentDao.deletePaymentFromDB(payment))
            || (!productDao.updateProductInDB(product))) {
                daoFactory.rollbackTransaction();
                return false;
            }
            daoFactory.commitTransaction();
            return true;
        } catch (DataBaseConnectionException | IncorrectPropertyException | DataNotFoundException ex) {
            return false;
        }
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
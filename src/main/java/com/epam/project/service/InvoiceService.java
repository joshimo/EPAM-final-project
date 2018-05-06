package com.epam.project.service;

import com.epam.project.dao.*;
import com.epam.project.domain.*;
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
    @Button
    public List<Invoice> findAllInvoices() {
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

    @Button
    public List<Invoice> findNewInvoices() {
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

    @Button
    public List<Invoice> findInvoicesByUser(String userName) {
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

    @Button
    public Invoice findInvoiceByOrderNumber(Long orderNum) {
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

    @Button
    public boolean addInvoice(Invoice invoice) {
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
                if ((product.getQuantity() - payment.getQuantity()) < 0 ) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
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

    @Button
    public boolean updateInvoice(Invoice invoice) {
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

    @Button
    public boolean deleteInvoice(Long orderCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        //ToDO: simplify if
        if ((invoice == null) || (invoice.getStatus() != OrderStatus.CREATED))
            return false;
        return deleteInvoice(invoice);
    }

    private boolean deleteInvoice(Invoice invoice) {
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

    @Button
    public boolean cancelInvoice(Long orderCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        //ToDO: simplify if
        if ((invoice == null) || (invoice.getStatus() != OrderStatus.CREATED))
            return false;
        return cancelInvoice(invoice);
    }

    private boolean cancelInvoice(Invoice invoice) {
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
                product.setQuantity(product.getQuantity() + payment.getQuantity());
                payment.setStatusId(OrderStatus.CANCELLED);
                if ((!productDao.updateProductInDB(product)) || (!paymentDao.updatePaymentInDB(payment))) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
            }
            invoice.setStatus(OrderStatus.CANCELLED);
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

    /** Special methods */
    @Button
    public boolean removeProductFromInvoice(Long orderCode, String productCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        //ToDO: simplify if
        if ((invoice == null) || (invoice.getStatus() != OrderStatus.CREATED))
            return false;
        return removeProductFromInvoice(invoice, productCode);
    }

    private boolean removeProductFromInvoice(Invoice invoice, String productCode) {
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

    @Button
    public boolean closeInvoice (Long orderCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        //ToDO: simplify if
        if ((invoice == null) || (invoice.getStatus() != OrderStatus.CREATED))
            return false;
        return closeInvoice(invoice);
    }

    private boolean closeInvoice (Invoice invoice) {
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

    @Button
    public Invoice createInvoiceFromUserCart(UserCart userCart) throws InvoiceServiceException {
        return createInvoiceFromUserCart(userCart, System.currentTimeMillis());
    }

    public Invoice createInvoiceFromUserCart(UserCart userCart, Long orderCode) throws InvoiceServiceException {
        Invoice invoice = new Invoice();
        invoice.setOrderCode(orderCode);
        invoice.setUserName(userCart.getUserName());
        invoice.setStatus(OrderStatus.CREATED);
        invoice.setOrderNotes(userCart.getOrderNotes());
        for (Map.Entry<String, Double> unit : userCart.getProducts().entrySet()) {
            Product product;
            try {
                product = ProductService.findProductByCode(unit.getKey());
            } catch (ProductServiceException ex) {
                throw new InvoiceServiceException("Product " + unit.getKey() + " not found in DB");
            }
            invoice.addProduct(product);
            Payment payment = new Payment();
            payment.setOrderCode(orderCode);
            payment.setProductCode(unit.getKey());
            payment.setQuantity(unit.getValue());
            payment.setPaymentValue(payment.getQuantity() * product.getCost());
            payment.setStatusId(OrderStatus.CREATED);
            payment.setPaymentNotes(userCart.getOrderNotes());
            invoice.addPayment(payment);
        }
        return invoice;
    }

    private boolean validateInvoice(Invoice invoice) {
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

    private boolean removePayment(Payment payment) {
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

    private synchronized boolean addPaymentsToInvoice(Invoice invoice, IPaymentDao paymentDao, IProductDao productDao) {
        List<Payment> payments;
        try {
            payments = paymentDao.findAllPaymentsByOrderCode(invoice.getOrderCode());
            if (payments == null)
                return false;
            for (Payment payment : payments) {
                String productCode = payment.getProductCode();
                Product product = productDao.findProductByCode(productCode);
                invoice.addPayment(payment);
                invoice.addProduct(product);
            }
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            return false;
        }
        return true;
    }
}
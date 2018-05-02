package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.domain.*;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class InvoiceDaoTester {

    private static IInvoiceDao invoiceDao;
    private static List<Invoice> invoices;

    private static final Logger log = Logger.getLogger(ProductDaoTester.class);

    public InvoiceDaoTester() throws DataBaseNotSupportedException, IncorrectPropertyException, DataBaseConnectionException {
        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        invoiceDao = daoFactory.getInvoiceDao();
    }

    public static void main(String... args) throws Exception {
        InvoiceDaoTester invoiceDaoTester = new InvoiceDaoTester();
        invoiceDaoTester.testFind(2L, "Yaroslav");
        log.info("Invoice added: " + invoiceDaoTester.testAddInvoice());
        //log.info("Invoice updated: " + invoiceDaoTester.testUpdateInvoice(1524747335490L));
        //log.info("Invoice deleted: " + invoiceDaoTester.testDeleteInvoice(1524747335490L));
    }

    private void testFind(Long order, String userName) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        log.info("\nAll invoices:");
        log.info(invoiceDao.findAllInvoices());
        log.info("\nProduct by User = " + order);
        log.info(invoiceDao.findAllInvoicesByUser(userName));
        log.info("\nProduct by Order num. = " + order);
        log.info(invoiceDao.findInvoiceByOrderNumber(order));
    }

    public boolean testAddInvoice() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException, DataBaseNotSupportedException {
        Invoice invoice = createTestInvoice();
        return invoiceDao.addInvoiceToDB(invoice);
    }

    private boolean testUpdateInvoice(Long orderNum) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Invoice inv = invoiceDao.findInvoiceByOrderNumber(orderNum);
        String notes = "Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        inv.setOrderNotes(notes);
        inv.setStatus(OrderStatus.FINISHED);
        Map<String, Payment> payments = inv.getPayments();
        for (Map.Entry<String, Payment> paymentEntry : payments.entrySet()) {
            paymentEntry.getValue().setQuantity(paymentEntry.getValue().getQuantity() + 20);
            paymentEntry.getValue().setPaymentValue(paymentEntry.getValue().getPaymentValue() + 20);
            paymentEntry.getValue().setPaymentNotes(notes);
        }
        return invoiceDao.updateInvoiceInDB(inv);
    }

    private boolean testDeleteInvoice(Long code) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Invoice inv = invoiceDao.findInvoiceByOrderNumber(code);
        return invoiceDao.deleteInvoiceFromDB(inv);
    }

    private Invoice createTestInvoice() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException, DataBaseNotSupportedException  {
        Long orderCode = System.currentTimeMillis();
        String userName = "Yaroslav";
        String note = "Created by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        String productCode1 = "C001";
        String productCode2 = "C002";
        Double quantity1 = 1.0;
        Double quantity2 = 1.0;
        Double value1 = 0.01;
        Double value2 = 0.01;

        Invoice invoice = new Invoice();
        invoice.setOrderCode(orderCode);
        invoice.setUserName(userName);
        invoice.setStatus(OrderStatus.CREATED);
        invoice.setOrderNotes(note);

        Payment p1 = new Payment();
        p1.setOrderCode(orderCode);
        p1.setProductCode(productCode1);
        p1.setQuantity(quantity1);
        p1.setPaymentValue(value1);
        p1.setStatusId(OrderStatus.CREATED);
        p1.setPaymentNotes(note);

        Payment p2 = new Payment();
        p2.setOrderCode(orderCode);
        p2.setProductCode(productCode2);
        p2.setQuantity(quantity2);
        p2.setPaymentValue(value2);
        p2.setStatusId(OrderStatus.CREATED);
        p2.setPaymentNotes(note);

        Product pr1 = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL).getProductDao().findProductByCode(productCode1);
        Product pr2 = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL).getProductDao().findProductByCode(productCode1);

        invoice.addProduct(productCode1, pr1);
        invoice.addProduct(productCode2, pr2);
        invoice.addPayment(productCode1, p1);
        invoice.addPayment(productCode2, p2);

        return invoice;
    }
}


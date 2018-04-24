package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.*;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.util.List;

public class InvoiceDaoTester {

    private static IInvoiceDao invoiceDao;
    private static List<Invoice> invoices;

    public InvoiceDaoTester() throws DataBaseNotSupportedException, IncorrectPropertyException {
        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
    }

    public static void main(String... args) throws Exception {
        InvoiceDaoTester invoiceDaoTester = new InvoiceDaoTester();
        invoiceDaoTester.testInvoiceDao();
    }

    public void testInvoiceDao() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        invoiceDao = new InvoiceDaoImpl();
        Invoice invoice = createTestInvoice();
        System.out.println(invoiceDao.addInvoiceToDB(invoice));
        /*System.out.println("All orders\n");
        invoices = invoiceDao.findAllInvoices();
        System.out.println(invoices);*/
        System.out.println("All orders by user 'Yaroslav'\n");
        invoices = invoiceDao.findAllInvoicesByUser("Yaroslav");
        System.out.println(invoices);
    }

    private Invoice createTestInvoice() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException  {
        Long orderCode = 998L;
        String userName = "Yaroslav";
        String note = "Created by " + this.getClass().getSimpleName();
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

        Product pr1 = new ProductDaoImpl().findProductByCode(productCode1);
        Product pr2 = new ProductDaoImpl().findProductByCode(productCode2);

        invoice.addProduct(productCode1, pr1);
        invoice.addProduct(productCode2, pr2);
        invoice.addPayment(productCode1, p1);
        invoice.addPayment(productCode2, p2);

        return invoice;
    }
}

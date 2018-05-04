import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.OrderStatus;
import com.epam.project.domain.Payment;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.*;
import com.epam.project.service.InvoiceService;
import com.epam.project.service.ProductService;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class InvoiceServiceTest {

    private static final Logger log = Logger.getLogger(InvoiceServiceTest.class);
    final static String USER_NAME = "Client";
    final static Long ORDER_NUM = 1000L;
    private static Invoice testInvoice;
    private static Invoice badInvoice;

    @BeforeClass
    public static void init() throws UnknownUserException  {
        log.info("Starting tests");
    }

    @AfterClass
    public static void close() throws UnknownUserException  {
        log.info("Closing tests");
        System.gc();
    }

    private Invoice createTestInvoice() {

        Long orderCode = 1000L;
        String userName = "Yaroslav";
        String note = "Created by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        String productCode1 = "C001";
        String productCode2 = "C002";
        Double quantity1 = 1.0;
        Double quantity2 = 1.0;
        Double value1 = 1.0;
        Double value2 = 1.0;

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

        Product pr1;
        Product pr2;

        try {
            pr1 = ProductService.findProductByCode(productCode1);
            pr2 = ProductService.findProductByCode(productCode2);
            invoice.addProduct(productCode1, pr1);
            invoice.addProduct(productCode2, pr2);
            invoice.addPayment(productCode1, p1);
            invoice.addPayment(productCode2, p2);
        } catch (ProductServiceException ex) {
            log.error(ex);
        }

        return invoice;
    }

    private Invoice createBadInvoice() {

        Long orderCode = 1001L;
        String userName = "Yaroslav";
        String note = "Created by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        String productCode1 = "C001";
        String productCode2 = "C002";
        Double quantity1 = 1.0;
        Double quantity2 = 1.0;
        Double value1 = 1.0;
        Double value2 = 1.0;

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
        p2.setOrderCode(orderCode + 1);
        p2.setProductCode(productCode2);
        p2.setQuantity(quantity2);
        p2.setPaymentValue(value2);
        p2.setStatusId(OrderStatus.CREATED);
        p2.setPaymentNotes(note);

        Product pr1;
        Product pr2;

        try {
            pr1 = ProductService.findProductByCode(productCode1);
            pr2 = ProductService.findProductByCode(productCode2);
            invoice.addProduct(productCode1, pr1);
            invoice.addProduct(productCode2, pr2);
            invoice.addPayment(productCode1, p1);
            invoice.addPayment(productCode2, p2);
        } catch (ProductServiceException ex) {
            log.error(ex);
        }

        return invoice;
    }


    /** tests */

    @Test
    public void findAllInvoicesTest() {
        List<Invoice> invoices = InvoiceService.findAllInvoices();
        log.info(invoices);
        assertTrue(invoices.size() > 0);
    }

    @Test
    public void findNewInvoicesTest() {
        List<Invoice> newInvoices = InvoiceService.findNewInvoices();
        assertTrue(newInvoices.size() > 0);
    }

    @Test
    public void findInvoicesByUserTest() {
        List<Invoice> invoicesByUser = InvoiceService.findInvoicesByUser(USER_NAME);
        log.info(invoicesByUser);
        assertTrue(invoicesByUser.size() > 0);
    }

    @Test
    public void findInvoicesByOrderNumberTest() {
        Invoice invoice = InvoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        log.info(invoice);
        assertTrue(invoice.getOrderCode() == ORDER_NUM);
    }

    @Test
    public void addInvoiceTest1() {
        testInvoice = createTestInvoice();
        assertTrue(InvoiceService.addInvoice(testInvoice));
    }

    @Test
    public void addInvoiceTest2() {
        badInvoice = createBadInvoice();
        assertFalse(InvoiceService.addInvoice(badInvoice));
    }

    @Test
    public void updateInvoiceTest() {
        Invoice invoice = InvoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        for (Map.Entry<String, Payment> paymentEntry : invoice.getPayments().entrySet())
            paymentEntry.getValue().setPaymentNotes("Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        invoice.setOrderNotes("Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        assertTrue(InvoiceService.updateInvoice(invoice));
    }

    @Test
    public void deleteInvoiceTest() {
        Invoice invoice = InvoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        assertTrue(InvoiceService.deleteInvoice(invoice));
    }
}

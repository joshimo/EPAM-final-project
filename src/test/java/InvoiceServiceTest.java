import com.epam.project.domain.*;
import com.epam.project.exceptions.*;
import com.epam.project.service.InvoiceService;
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
    private final static String USER_NAME = "Guest";
    private final static Long ORDER_NUM = 1000L;
    private static Invoice testInvoice;
    private static Invoice badInvoice;
    private static InvoiceService invoiceService;

    @BeforeClass
    public static void init() {
        log.info("Starting tests");
        invoiceService = new InvoiceService();
    }

    @AfterClass
    public static void close() {
        log.info("Closing tests");
        System.gc();
    }

    private Invoice createBadInvoice() throws InvoiceServiceException {
        Invoice invoice = createTestInvoice(createTestCart());

        return invoice;
    }

    private UserCart createTestCart() {
        UserCart cart = new UserCart(USER_NAME);
        cart.setOrderNotes("Created by " + getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        cart.addProduct("D001", 10.0);
        cart.addProduct("D002", 10.0);
        cart.addProduct("D009", 50.0);
        cart.addProduct("D010", 50.0);
        return cart;
    }

    private Invoice createTestInvoice(UserCart cart) throws InvoiceServiceException {
        return invoiceService.createInvoiceFromUserCart(cart, ORDER_NUM);
    }

    /** tests */
    @Test
    public void findAllInvoicesTest() {
        List<Invoice> invoices = invoiceService.findAllInvoices();
        log.info(invoices);
        assertTrue(invoices.size() > 0);
    }

    @Test
    public void findNewInvoicesTest() {
        List<Invoice> newInvoices = invoiceService.findNewInvoices();
        log.info(newInvoices);
        assertTrue(newInvoices.size() > 0);
    }

    @Test
    public void findInvoicesByUserTest() {
        List<Invoice> invoicesByUser = invoiceService.findInvoicesByUser(USER_NAME);
        log.info(invoicesByUser);
        assertTrue(invoicesByUser.size() > 0);
    }

    @Test
    public void findInvoicesByOrderNumberTest() {
        Invoice invoice = invoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        log.info(invoice);
        assertTrue(invoice.getInvoiceCode().equals(ORDER_NUM));
    }

    @Test
    public void addInvoiceTest1() throws InvoiceServiceException {
        UserCart cart = createTestCart();
        testInvoice = createTestInvoice(cart);
        assertTrue(invoiceService.addInvoice(testInvoice));
    }

    @Test
    public void addInvoiceTest2() throws InvoiceServiceException {
        badInvoice = createBadInvoice();
        assertFalse(invoiceService.addInvoice(badInvoice));
    }

    @Test
    public void updateInvoiceTest() {
        Invoice invoice = invoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        String notes = "Updated by " + getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        for (Map.Entry<String, Payment> paymentEntry : invoice.getPayments().entrySet()) {
            paymentEntry.getValue().setQuantity(paymentEntry.getValue().getQuantity() + 10d);
            paymentEntry.getValue().setPaymentNotes(notes);
        }
        invoice.setInvoiceNotes(notes);
        assertTrue(invoiceService.updateInvoice(invoice));
    }

    @Test
    public void deleteInvoiceTest() {
        Invoice invoice = invoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        boolean result = invoiceService.deleteInvoice(ORDER_NUM);
        if (invoice.getStatus() == InvoiceStatus.CREATED)
            assertTrue(result);
        else
            assertFalse(result);
    }

    @Test
    public void removeProductFromInvoiceTest() {
        Invoice invoice = invoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        boolean result = invoiceService.removeProductFromInvoice(ORDER_NUM, "D001");
        if (invoice.getStatus() == InvoiceStatus.CREATED)
            assertTrue(result);
        else
            assertFalse(result);
    }

    @Test
    public void closeInvoiceTest() {
        Invoice invoice = invoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        boolean result = invoiceService.closeInvoice(ORDER_NUM);
        if (invoice.getStatus() == InvoiceStatus.CREATED)
            assertTrue(result);
        else
            assertFalse(result);
    }

    @Test
    public void cancelInvoiceTest() {
        Invoice invoice = invoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        boolean result = invoiceService.cancelInvoice(ORDER_NUM);
        if (invoice.getStatus() == InvoiceStatus.CREATED)
            assertTrue(result);
        else
            assertFalse(result);
    }

    @Test
    public void payByInvoiceTest() {
        Invoice invoice = invoiceService.findInvoiceByOrderNumber(ORDER_NUM);
        boolean result = invoiceService.payByInvoice(ORDER_NUM);
        if ((invoice.getStatus() == InvoiceStatus.CREATED) && (!invoice.getPaid()))
            assertTrue(result);
        else
            assertFalse(result);
    }
}
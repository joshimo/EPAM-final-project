import com.epam.project.domain.*;
import com.epam.project.exceptions.*;
import com.epam.project.service.InvoiceService;
import com.epam.project.service.TransactionService;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private static final Logger log = Logger.getLogger(TransactionServiceTest.class);
    private static Transaction transaction;
    private static Invoice invoice;
    private static InvoiceService invoiceService;
    private static TransactionService transactionService;

    @BeforeClass
    public static void init() throws TransactionServiceException {
        invoiceService = new InvoiceService();
        transactionService = new TransactionService();
        invoice = invoiceService.findInvoiceByOrderNumber(1L);
    }

    @AfterClass
    public static void destroy() {
        invoiceService = null;
        invoice = null;
        System.gc();
    }

    @Test
    public void addTransactionTest() {
        Set<String> productCodes = invoice.getPayments().keySet();
        String notes = "Created by " + UserServiceTest.class.getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        for (String productCode : productCodes) {
            Payment payment = invoice.getPayments().get(productCode);
            Transaction transaction = transactionService.createTransactionFromPayment(payment, invoice.getUserName(), notes);
            if (!transactionService.addTransaction(transaction))
                fail("transaction was not added");
        }
    }

    @Test
    public void findTransactionsTest() throws TransactionServiceException {
        List<Transaction> transactions = transactionService.findAllTransactions();
        log.info(transactions);
        assertTrue(transactions.size() > 0);
    }
}

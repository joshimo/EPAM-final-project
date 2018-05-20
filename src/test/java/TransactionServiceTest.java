import com.epam.project.domain.*;
import com.epam.project.exceptions.*;
import com.epam.project.service.*;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private static final Logger log = Logger.getLogger(TransactionServiceTest.class);
    private static Transaction transaction;
    private static Invoice invoice;
    private static IInvoiceServ invoiceService;
    private static ITransactionServ transactionService;

    @BeforeClass
    public static void init() throws TransactionServiceException {
        invoiceService = ServiceFactory.getInvoiceService();
        transactionService = ServiceFactory.getTransactionService();
        invoice = invoiceService.findInvoiceByOrderNumber(1L);
    }

    @AfterClass
    public static void destroy() {
        invoiceService = null;
        invoice = null;
        System.gc();
    }

    @Test
    @Ignore
    public void findTransactionsTest() throws TransactionServiceException {
        List<Transaction> transactions = transactionService.findAllTransactions();
        log.info(transactions);
        assertTrue(transactions.size() > 0);
    }
}

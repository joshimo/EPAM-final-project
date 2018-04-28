import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import com.epam.project.logic.ProductLogic;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductLogicTest {

    static Product correctProduct;
    static Product uncorrectProduct;

    @BeforeClass
    public static void init() throws
            DataBaseNotSupportedException,
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        correctProduct = ProductLogic.findProductByCode("D001");
        uncorrectProduct = ProductLogic.findProductByCode("D001");
    }

    /** Product Validator tests */

    @Test
    public void testProductValidator1() {
        assertTrue(ProductLogic.validateProductData(correctProduct));
    }

    @Test
    public void testProductValidator2() {
        uncorrectProduct.setCode("");
        uncorrectProduct.setNameEn(null);
        uncorrectProduct.setNameRu("");
        assertFalse(ProductLogic.validateProductData(uncorrectProduct));
    }
}


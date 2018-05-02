import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import com.epam.project.logic.ProductLogic;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class ProductLogicTest {

    private static Product correctProduct;
    private static Product uncorrectProduct;
    private static final String PRODUCT_CODE = "TEST001";

    private static final Logger log = Logger.getLogger(ProductLogicTest.class);

    @BeforeClass
    public static void init() throws
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        log.info("Starting tests");
        correctProduct = ProductLogic.findProductByCode("D001");
        uncorrectProduct = ProductLogic.findProductByCode("D001");
    }

    @AfterClass
    public static void close() throws
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        try {
            ProductLogic.deleteProduct(PRODUCT_CODE);
        } catch (Exception e) {}
        log.info("Finishing tests");
        correctProduct = null;
        uncorrectProduct = null;
        System.gc();
    }

    private Product createTestProduct() {
        Product product = new Product().addCode(PRODUCT_CODE).addAvailable(true)
                .addNameEn("Royal Canin Kitten 30 - 120").addNameRu("Royal Canin Kitten 30 - 120")
                .addDescriptionEn("Royal Canin dry feed for kittens 30 - 120 month")
                .addDescriptionRu("Корм Роял Канин для котят в возрасте 30 - 120 месяцeв")
                .addCost(247.50).addQuantity(50.0)
                .addUomEn("kg").addUomRu("кг")
                .addNotesEn("Created by " + this.getClass().getSimpleName()).addNotesRu("Сгенерировано классом " + this.getClass().getSimpleName());
        return product;
    }

    /** Product Validator tests */
    @Test
    @Ordinal(order = 1)
    public void testProductValidator1() {
        assertTrue(ProductLogic.validateProductData(correctProduct));
    }

    @Test
    @Ordinal(order = 2)
    public void testProductValidator2() {
        uncorrectProduct.setCode("");
        uncorrectProduct.setNameEn(null);
        uncorrectProduct.setNameRu("");
        assertFalse(ProductLogic.validateProductData(uncorrectProduct));
    }

    /** Product CRUD operations tests */
    @Test
    @Ordinal(order = 3)
    public void testFindAllProducts() throws
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        assertTrue(ProductLogic.findAllProducts().size() > 0);
    }

    @Test
    @Ordinal(order = 4)
    public void testFindProductByCode() throws
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        assertEquals(correctProduct, ProductLogic.findProductByCode("D001"));
    }

    @Test
    @Ordinal(order = 5)
    public void testAddProduct() throws
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        Product testProduct = createTestProduct();
        log.info(testProduct);
        boolean result = ProductLogic.addProduct(testProduct);
        Product product = ProductLogic.findProductByCode(PRODUCT_CODE);
        assertTrue(result && product.equals(testProduct));
    }

    @Test
    @Ordinal(order = 6)
    public void testUpdateProduct() throws
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        String updEn = "Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        String updRu = "Обновлено " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        Product product = ProductLogic.findProductByCode(PRODUCT_CODE);
        product.setNotesEn(updEn);
        product.setNotesRu(updRu);
        boolean result = ProductLogic.updateProduct(product);
        assertTrue(result);
    }

    @Test
    @Ordinal(order = 7)
    public void testDeleteProduct() throws
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        Product testProduct = createTestProduct();
        boolean result = ProductLogic.deleteProduct(testProduct);
        assertTrue(result);
    }
}


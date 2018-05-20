import com.epam.project.domain.Product;
import com.epam.project.exceptions.ProductServiceException;
import com.epam.project.service.IProductServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class ProductServiceTest {

    private static IProductServ productService;
    private static Product correctProduct;
    private static Product uncorrectProduct;
    private static final String PRODUCT_CODE = "TEST001";

    private static final Logger log = Logger.getLogger(ProductServiceTest.class);

    @BeforeClass
    public static void init() throws ProductServiceException  {
        log.info("Starting tests");
        productService = ServiceFactory.getProductService();
        correctProduct = productService.findProductByCode("D001");
        uncorrectProduct = productService.findProductByCode("D001");
    }

    @AfterClass
    public static void close() throws ProductServiceException {
        try {
            //ProductService.deleteProduct(PRODUCT_CODE);
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

    /** Product CRUD operations tests */
    @Test
    @Ignore
    @Ordinal(order = 3)
    public void testFindAllProducts() throws ProductServiceException {
        assertTrue(productService.findAllProducts().size() > 0);
    }

    @Test
    @Ignore
    @Ordinal(order = 4)
    public void testFindProductByCode() throws ProductServiceException {
        assertEquals(correctProduct, productService.findProductByCode("D001"));
    }

    @Test
    @Ignore
    @Ordinal(order = 5)
    public void testAddProduct() throws ProductServiceException {
        Product testProduct = createTestProduct();
        log.info(testProduct);
        boolean result = productService.addProduct(testProduct);
        Product product = productService.findProductByCode(PRODUCT_CODE);
        assertTrue(result && product.equals(testProduct));
    }

    @Test
    @Ignore
    @Ordinal(order = 6)
    public void testUpdateProduct() throws ProductServiceException {
        String updEn = "Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        String updRu = "Обновлено " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis());
        Product product = productService.findProductByCode(PRODUCT_CODE);
        product.setNotesEn(updEn);
        product.setNotesRu(updRu);
        boolean result = productService.updateProduct(product);
        assertTrue(result);
    }

    @Test
    @Ignore
    @Ordinal(order = 7)
    public void testDeleteProduct() {
        Product testProduct = createTestProduct();
        boolean result = productService.deleteProduct(testProduct);
        assertTrue(result);
    }
}
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

@Deprecated
public class ProductDaoTester {

    private static IProductDao productDao;
    private static List<Product> products;

    private static final Logger log = Logger.getLogger(ProductDaoTester.class);

    public ProductDaoTester() throws DataBaseNotSupportedException, IncorrectPropertyException, DataBaseConnectionException {
        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        productDao = daoFactory.getProductDao();
    }

    public static void main(String... args) throws Exception {
        ProductDaoTester productDaoTester = new ProductDaoTester();
        //productDaoTester.testFind(11, "D010");
        //log.info("Product updated: " + productDaoTester.testUpdateProduct("C002A"));
        log.info("Product added: " + productDaoTester.testAddProduct(productDaoTester.createTestProduct("C002A")));
        //log.info("Product deleted: " + productDaoTester.testDeleteProduct("C002A"));
    }

    private Product createTestProduct(String code) {
        Product product = new Product().addCode(code).addAvailable(true)
                .addNameEn("Royal Canin Kitten 30 - 120").addNameRu("Royal Canin Kitten 30 - 120")
                .addDescriptionEn("Royal Canin dry feed for kittens 30 - 120 month")
                .addDescriptionRu("Корм Роял Канин для котят в возрасте 30 - 120 месяцeв")
                .addCost(247.50).addQuantity(50.0)
                .addUomEn("kg").addUomRu("кг")
                .addNotesEn("Created by " + this.getClass().getSimpleName()).addNotesRu("Сгенерировано классом " + this.getClass().getSimpleName());
        return product;
    }

    private boolean testAddProduct(Product product) throws IncorrectPropertyException, DataBaseConnectionException {
        return productDao.addProductToDB(product);
    }

    private boolean testUpdateProduct(String code) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Product product = productDao.findProductByCode(code);
        String productCode = product.getCode();
        if (productCode.endsWith("A"))
            product.setCode(productCode.replace("A", ""));
        else
            product.setCode(productCode.concat("A"));
        product.setNotesEn("Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        product.setNotesRu("Обновлено " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        return productDao.updateProductInDB(product);
    }

    private boolean testDeleteProduct(String code) throws IncorrectPropertyException, DataBaseConnectionException {
        return productDao.deleteProductFromDB(code);
    }

    private void testFind(Integer id, String code) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        log.info("All products:");
        log.info(productDao.findAllProductsInDB());
        log.info("Product by id = " + id);
        log.info(productDao.findProductById(id));
        log.info("Product by code = " + code);
        log.info(productDao.findProductByCode(code));
    }
}
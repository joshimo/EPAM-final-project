package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.*;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Timestamp;
import java.util.List;

public class ProductDaoTester {

    private static IProductDao productDao;
    private static List<Product> products;

    public ProductDaoTester() throws DataBaseNotSupportedException, IncorrectPropertyException {
        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        productDao = daoFactory.getProductDao();
    }

    public static void main(String... args) throws Exception {
        ProductDaoTester productDaoTester = new ProductDaoTester();
        productDaoTester.testFind(11, "D010");
        //System.out.println("Product updated: " + productDaoTester.testUpdateProduct("C002"));
        //System.out.println("Product added: " + productDaoTester.testAddProduct(productDaoTester.createTestProduct("C002A")));
        //System.out.println("Product deleted: " + productDaoTester.testDeleteProduct("C002A"));
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

    private boolean testAddProduct(Product product) {
        return productDao.addProductToDB(product);
    }

    private boolean testUpdateProduct(String code) {
        Product product = productDao.findProductByCode(code);
        product.setNotesEn("Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        product.setNotesRu("Обновлено " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        return productDao.updateProductInDB(product);
    }

    private boolean testDeleteProduct(String code) {
        return productDao.deleteProductFromDB(code);
    }

    private void testFind(Integer id, String code) {
        System.out.println("All products:");
        System.out.println(productDao.findAllProductsInDB());
        System.out.println("Product by id = " + id);
        System.out.println(productDao.findProductById(id));
        System.out.println("Product by code = " + code);
        System.out.println(productDao.findProductByCode(code));
    }

}
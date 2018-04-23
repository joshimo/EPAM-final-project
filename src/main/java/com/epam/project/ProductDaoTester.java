package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.*;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;

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
        productDaoTester.testProductDao();
    }

    public void testProductDao() {
        Product product = new Product().addCode("C002A").addAvailable(true)
                .addNameEn("Royal Canin Kitten 3 - 120").addNameRu("Royal Canin Kitten 3 - 120")
                .addDescriptionEn("Royal Canin dry feed for kittens 3 - 120 month")
                .addDescriptionRu("Корм Роял Канин для котят в возрасте 3 - 120 месяцeв")
                .addCost(247.50).addQuantity(50.0)
                .addUomEn("kg").addUomRu("кг")
                .addNotesEn("Added by ProductDaoTester runner").addNotesRu("Сгенерировано классом ProductDaoTester");
        //System.out.println(productDao.addProductToDB(product));
        //System.out.println(productDao.updateProductInDB(product));
        System.out.println(productDao.deleteProductFromDB("C002A"));
        System.out.println("All products\n");
        products = productDao.findAllProductsInDB();
        System.out.println(products);
        System.out.println("\nProduct by id = 11");
        System.out.println(productDao.findProductById(11));
        System.out.println("\nProduct by code = D005");
        System.out.println(productDao.findProductByCode("D005"));
    }
}

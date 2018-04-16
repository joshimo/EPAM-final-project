package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.Order;
import com.epam.project.entities.Product;
import com.epam.project.entities.User;

import java.sql.Connection;
import java.util.List;

public class Application {

    private static IProductDao productDao;
    private static List<Product> products;
    private static IUserDao userDao;
    private static List<User> users;
    private static IOrderDao orderDao;
    private static List<Order> orders;

    public static void main(String... args) throws Exception {
        Connection connection = Connector.createConnection();
        productDao = new ProductDaoImplementation(connection);
        products = productDao.findAllProductsInDB();
        Product product = productDao.findProductById(12).addAvailable(true)
                .addNameEn("Royal Canin Kitten").addNameRu("Royal Canin Kitten")
                .addDescriptionEn("Royal Canin dry feed for kittens 4 - 12 month")
                .addDescriptionRu("Корм Роял Канин для котят в возрасте 4 - 12 месяцeв")
                .addCost(247.50).addQuantity(50.0)
                .addUomEn("kg").addUomRu("кг")
                .addNotesEn("Added by Application runner").addNotesRu("Сгенерировано классом Application");
        //productDao.addProductToDB(product);
        //productDao.updateProductInDB(product);
        System.out.println(productDao.deleteProductFromDB(13));
        System.out.println(productDao.findAllProductsInDB());
        Connector.closeConnection(connection);
    }
}

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
        Connector connector = Connector.getInstance();
        //Connection connection = Connector.createConnection();
        Connection connection = connector.getConnection();
        productDao = new ProductDaoImplementation(connection);
        /*Product product = new Product().addCode("C002A").addAvailable(true)
                .addNameEn("Royal Canin Kitten 4 - 12").addNameRu("Royal Canin Kitten 4 - 12")
                .addDescriptionEn("Royal Canin dry feed for kittens 4 - 12 month")
                .addDescriptionRu("Корм Роял Канин для котят в возрасте 4 - 12 месяцeв")
                .addCost(247.50).addQuantity(50.0)
                .addUomEn("kg").addUomRu("кг")
                .addNotesEn("Added by Application runner").addNotesRu("Сгенерировано классом Application");*/
        //productDao.addProductToDB(product);
        //productDao.updateProductInDB(product);
        //System.out.println(productDao.deleteProductFromDB(14));
        products = productDao.findAllProductsInDB();
        System.out.println(products);
        Connector.closeConnection(connection);
    }
}

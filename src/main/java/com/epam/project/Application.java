package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.Order;
import com.epam.project.entities.Product;
import com.epam.project.entities.User;
import com.epam.project.entities.UserRole;

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
        Application application = new Application();
        //application.testProductDao();
        application.testUserDao();
    }

    public void testUserDao() {
        userDao = new UserDaoImplementation();
        User user = new User("sd", "gge");
            user.setUserRole(UserRole.ADMIN);
            user.setNotes("Added by Application runner");
        //System.out.println(userDao.addUserToDB(user));
        System.out.println(userDao.updateUserInDB(user));
        System.out.println("All users\n");
        users = userDao.findAllUsersInDB();
        System.out.println(users);
        System.out.println("\nUser by id = 5");
        System.out.println(userDao.findUserById(5));
        System.out.println("\nUser by name = Yaroslav");
        System.out.println(userDao.findUserByName("Yaroslav"));
    }

    public void testProductDao(){
        productDao = new ProductDaoImplementation();
        Product product = new Product().addCode("C002A").addAvailable(true)
                .addNameEn("Royal Canin Kitten 0 - 13").addNameRu("Royal Canin Kitten 0 - 13")
                .addDescriptionEn("Royal Canin dry feed for kittens 0 - 13 month")
                .addDescriptionRu("Корм Роял Канин для котят в возрасте 0 - 13 месяцeв")
                .addCost(247.50).addQuantity(50.0)
                .addUomEn("kg").addUomRu("кг")
                .addNotesEn("Added by Application runner").addNotesRu("Сгенерировано классом Application");
        //System.out.println(productDao.updateProductInDB(product));
        //productDao.updateProductInDB(product);
        //System.out.println(productDao.deleteProductFromDB("C002A"));
        System.out.println("All products\n");
        products = productDao.findAllProductsInDB();
        System.out.println(products);
        System.out.println("\nProduct by id = 5");
        System.out.println(productDao.findProductById(5));
        System.out.println("\nProduct by code = D005");
        System.out.println(productDao.findProductByCode("D005"));
    }
}

package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.Order;
import com.epam.project.entities.OrderStatus;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.util.List;

public class OrderDaoTester {
    private static IOrderDao orderDao;
    private static List<Order> orders;

    public OrderDaoTester() throws DataBaseNotSupportedException, IncorrectPropertyException {
        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
    }

    public static void main(String... args) throws Exception {
        OrderDaoTester orderDaoTester = new OrderDaoTester();
        orderDaoTester.testOrderDao();
    }

    public void testOrderDao() throws DataBaseConnectionException {
        Connection connection = MySQLDaoFactory.getConnection();
        orderDao = new OrderDaoImpl(connection);
        Order order = new Order();
        order.setOrderId(4);
        order.setOrderCode(4L);
        order.setUserName("Yaroslav");
        order.setStatus(OrderStatus.CREATED);
        order.setOrderNotes("Created by OrderDaoTester runner");
        //System.out.println(orderDao.addOrderToDB(order));
        System.out.println(orderDao.updateOrderInDB(order));
        //System.out.println(orderDao.deleteOrderFromDB(order));
        System.out.println("All orders\n");
        orders = orderDao.findAllOrders();
        System.out.println(orders);
        System.out.println("\nOrders by user 'Client':");
        System.out.println(orderDao.findAllOrdersByUser("Client"));
        System.out.println("\nOrders by order code 1:");
        System.out.println(orderDao.findOrderById(1));
    }
}

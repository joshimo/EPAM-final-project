package com.epam.project.dao;

import com.epam.project.entities.Order;

import java.util.List;

public interface IOrderDao {

    List<Order> retrieveAllOrdersByUser(Integer userId);

    Order retrieveOrderById(Integer id);

    boolean addOrderToDB(Order order) throws Exception;

    boolean updateOrderInDB(Order order) throws Exception;

    boolean deleteOrderFromDB(Order order) throws Exception;

}

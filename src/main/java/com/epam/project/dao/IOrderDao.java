package com.epam.project.dao;

import com.epam.project.entities.Order;

import java.util.List;

public interface IOrderDao {

    <T extends Exception> List<Order> findAllOrders() throws T;

    <T extends Exception> List<Order> findAllOrdersByUser(Integer userId) throws T;

    <T extends Exception> Order findOrderById(Integer id) throws T;

    <T extends Exception> boolean addOrderToDB(Order order) throws T;

    <T extends Exception> boolean updateOrderInDB(Order order) throws T;

    <T extends Exception> boolean deleteOrderFromDB(Order order) throws T;

    <T extends Exception> boolean closeOrder(Order order) throws T;
}

package com.epam.project.dao;

import com.epam.project.entities.Order;
import com.epam.project.entities.OrderStatus;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderDaoImplementation implements IOrderDao {

    private static String SQL_selectAll = "SELECT orders.order_id, " +
            "users.user_name, " +
            "order_status.status_description, " +
            "orders.order_date, " +
            "orders.order_notes, " +
            "stock.product_code, " +
            "payments.quantity " +
            "FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id " +
            "JOIN users ON orders.user_id=users.user_id " +
            "JOIN payments ON orders.order_id=payments.order_id " +
            "JOIN stock ON stock.product_code=payments.product_code ORDER BY quantity;";

    private static String SQL_selectById = "SELECT orders.order_id, " +
            "users.user_name, " +
            "order_status.status_description, " +
            "orders.order_date, " +
            "orders.order_notes, " +
            "stock.product_code, " +
            "payments.quantity " +
            "FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id " +
            "JOIN users ON orders.user_id=users.user_id " +
            "JOIN payments ON orders.order_id=payments.order_id " +
            "JOIN stock ON stock.product_code=payments.product_code " +
            "WHERE orders.order_id=?;";

    private static String SQL_selectByUser = "SELECT orders.order_id, " +
            "users.user_name, " +
            "order_status.status_description, " +
            "orders.order_date, " +
            "orders.order_notes, " +
            "stock.product_code, " +
            "payments.quantity " +
            "FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id " +
            "JOIN users ON orders.user_id=users.user_id " +
            "JOIN payments ON orders.order_id=payments.order_id " +
            "JOIN stock ON stock.product_code=payments.product_code " +
            "WHERE users.user_name=?;";

    private Mapper<ResultSet, Order> mapperFromDB = (ResultSet resultSet, Order order) -> {
        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserName(resultSet.getString("user_name"));
        order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("status_description")));
        order.setOrderDate(resultSet.getTimestamp("order_date"));
        order.setNotes(resultSet.getString("order_notes"));
    };

    public OrderDaoImplementation() {
    }

    @Override
    public List<Order> findAllOrders() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException  {
        List<Order> orders = new LinkedList<>();
        Connection connection = Connector.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer orderId = 0;
            Order order = new Order();
            while (resultSet.next()) {
                if (resultSet.getInt("order_id") == orderId) {
                    order.addProduct(resultSet.getString("product_code"), resultSet.getDouble("quantity"));
                }
                else {
                    order = new Order();
                    orderId = resultSet.getInt("order_id");
                    mapperFromDB.map(resultSet, order);
                    order.addProduct(resultSet.getString("product_code"), resultSet.getDouble("quantity"));
                    orders.add(order);
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new DataNotFoundException();
        } finally {
            Connector.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public List<Order> findAllOrdersByUser(Integer userId) {
        return null;
    }

    @Override
    public Order findOrderById(Integer id) {
        return null;
    }

    @Override
    public boolean addOrderToDB(Order order) throws Exception {
        return false;
    }

    @Override
    public boolean updateOrderInDB(Order order) throws Exception {
        return false;
    }

    @Override
    public boolean deleteOrderFromDB(Order order) throws Exception {
        return false;
    }

    @Override
    public <T extends Exception> boolean closeOrder(Order order) throws T {
        return false;
    }
}

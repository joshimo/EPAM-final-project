package com.epam.project.dao;

import com.epam.project.domain.Order;
import com.epam.project.domain.OrderStatus;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Deprecated
public class OrderDaoImpl extends GenericAbstractDao<Order> implements IOrderDao {

    private Connection connection;


    private static String SQL_selectAll = "SELECT * FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id;";
    private static String SQL_selectAllByUserName = "SELECT * FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id WHERE user_name=?;";
    private static String SQL_selectById = "SELECT * FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id WHERE order_id=?;";
    private static String SQL_addNew = "INSERT INTO project.orders (order_code, user_name, status_id, order_notes)" +
            " VALUES (?,?,?,?);";
    private static String SQL_update = "UPDATE project.orders SET order_code=?, user_name=?, status_id=?, order_notes=? " +
            "WHERE order_id=?;";
    private static String SQL_deleteById = "DELETE FROM project.orders WHERE order_id=?;";

    private Mapper<Order, PreparedStatement> mapperToDB = (Order order, PreparedStatement preparedStatement) -> {
        preparedStatement.setLong(1, order.getOrderCode());
        preparedStatement.setString(2, order.getUserName());
        preparedStatement.setInt(3, order.getStatus().ordinal() + 1);
        preparedStatement.setString(4, order.getOrderNotes());
    };

    private Mapper<ResultSet, Order> mapperFromDB = (ResultSet resultSet, Order order) -> {
        order.setOrderId(resultSet.getInt("order_id"));
        order.setOrderCode(resultSet.getLong("order_code"));
        order.setUserName(resultSet.getString("user_name"));
        order.setStatus(OrderStatus.valueOf(resultSet.getString("status_description")));
        order.setDate(resultSet.getTimestamp("order_date"));
        order.setOrderNotes(resultSet.getString("order_notes"));
    };

    public OrderDaoImpl(Connection connection) {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        this.connection = connection;
    }

    @Override
    public List<Order> findAllOrders() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        List<Order> orders = findAll(this.connection, Order.class, SQL_selectAll);
        return orders;
    }

    @Override
    public List<Order> findAllOrdersByUser(String userName) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        List<Order> orders = findAll(this.connection, Order.class, SQL_selectAll);
        List<Order> results = new LinkedList<>();
        for (Order order : orders)
            if (order.getUserName().equals(userName))
                results.add(order);
        return results;
    }

    @Override
    public Order findOrderById(Integer id) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
       Order order = findBy(this.connection, Order.class, SQL_selectById, id);
        return order;
    }

    @Override
    public boolean addOrderToDB(Order order) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        return addToDB(this.connection, order, SQL_addNew);
    }

    @Override
    public boolean updateOrderInDB(Order order) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        return updateInDB(connection, order, SQL_update, 5, order.getOrderId());
    }

    @Override
    public boolean deleteOrderFromDB(Order order) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        return deleteFromDB(connection, SQL_deleteById, order.getOrderId());
    }
}

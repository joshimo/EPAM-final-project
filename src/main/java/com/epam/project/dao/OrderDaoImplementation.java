package com.epam.project.dao;

import com.epam.project.entities.Order;
import com.epam.project.entities.OrderStatus;
import com.epam.project.entities.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderDaoImplementation implements IOrderDao {

    private static String SQL_selectAll = "SELECT orders.order_id, " +
            "users.user_name, " +
            "order_status.status_description, " +
            "orders.order_date, " +
            "orders.order_notes, " +
            "payments.product_code, " +
            "payments.quantity " +
            "FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id " +
            "JOIN users ON orders.user_id=users.user_id " +
            "JOIN payments ON orders.order_id=payments.order_id " +
            "ORDER BY orders.order_id;";

    private static String SQL_selectById = "SELECT orders.order_id, " +
            "users.user_name, " +
            "order_status.status_description, " +
            "orders.order_date, " +
            "orders.order_notes, " +
            "payments.product_code, " +
            "payments.quantity " +
            "FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id " +
            "JOIN users ON orders.user_id=users.user_id " +
            "JOIN payments ON orders.order_id=payments.order_id " +
            "WHERE orders.order_id=?;";

    private static String SQL_selectByUser = "SELECT orders.order_id, " +
            "users.user_name, " +
            "order_status.status_description, " +
            "orders.order_date, " +
            "orders.order_notes, " +
            "payments.product_code, " +
            "payments.quantity " +
            "FROM orders " +
            "JOIN order_status ON orders.status_id=order_status.status_id " +
            "JOIN users ON orders.user_id=users.user_id " +
            "JOIN payments ON orders.order_id=payments.order_id " +
            "WHERE users.user_id=? ORDER BY orders.order_id;";

    private static String SQL_add_order = "INSERT INTO project.orders " +
            "(user_id, status_id, order_notes) VALUES (?,?,?);";
    private static String SQL_add_payment = "INSERT INTO project.payments " +
            "(order_id, product_code, quantity, is_paid, payment_notes) VALUES (?,?,?,?,?);";

    private Mapper<ResultSet, Order> mapperFromDB = (ResultSet resultSet, Order order) -> {
        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserName(resultSet.getString("user_name"));
        order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("status_description")));
        order.setOrderDate(resultSet.getTimestamp("order_date"));
        order.setNotes(resultSet.getString("order_notes"));
    };

    private Mapper<Order, PreparedStatement> mapperToDB = (Order order, PreparedStatement preparedStatement) -> {
        //preparedStatement.setInt(1, order.getUserId());
        preparedStatement.setInt(2, order.getOrderStatus().ordinal() + 1);
        preparedStatement.setString(3, order.getNotes());
    };

    public OrderDaoImplementation() {
    }

    @Override
    public List<Order> findAllOrders() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException  {
        List<Order> orders = new LinkedList<>();
        Connection connection = MySQLDaoFactory.getConnection();
        IProductDao productDao = new ProductDaoImplementation();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer orderId = 0;
            Order order = new Order();
            // ToDo: unstable code, needs to be checked
            while (resultSet.next()) {
                Product product = productDao.findProductByCode(resultSet.getString("product_code"));
                if (resultSet.getInt("order_id") == orderId) {
                    order.addProduct(product, resultSet.getDouble("quantity"));
                }
                else {
                    order = new Order();
                    orderId = resultSet.getInt("order_id");
                    mapperFromDB.map(resultSet, order);
                    order.addProduct(product, resultSet.getDouble("quantity"));
                    orders.add(order);
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new DataNotFoundException();
        } finally {
            MySQLDaoFactory.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public List<Order> findAllOrdersByUser(Integer userId) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        List<Order> orders = new LinkedList<>();
        Connection connection = MySQLDaoFactory.getConnection();
        IProductDao productDao = new ProductDaoImplementation();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByUser);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer orderId = 0;
            Order order = new Order();
            // ToDo: unstable code, needs to be checked
            while (resultSet.next()) {
                Product product = productDao.findProductByCode(resultSet.getString("product_code"));
                if (resultSet.getInt("order_id") == orderId) {
                    order.addProduct(product, resultSet.getDouble("quantity"));
                }
                else {
                    order = new Order();
                    orderId = resultSet.getInt("order_id");
                    mapperFromDB.map(resultSet, order);
                    order.addProduct(product, resultSet.getDouble("quantity"));
                    orders.add(order);
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new DataNotFoundException();
        } finally {
            MySQLDaoFactory.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public Order findOrderById(Integer id) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Order order = new Order();
        Connection connection = MySQLDaoFactory.getConnection();
        IProductDao productDao = new ProductDaoImplementation();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // ToDo: unstable code, needs to be checked
            while (resultSet.next()) {
                Product product = productDao.findProductByCode(resultSet.getString("product_code"));
                mapperFromDB.map(resultSet, order);
                order.addProduct(product, resultSet.getDouble("quantity"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new DataNotFoundException();
        } finally {
            MySQLDaoFactory.closeConnection(connection);
        }
        return order;
    }

    @Override
    public boolean addOrderToDB(Order order) throws Exception {
        boolean result = false;
        Connection connection = MySQLDaoFactory.getConnection();
        List<PreparedStatement> statements = new LinkedList<>();
        try {
            /** Begin of transaction */
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_add_order);
            mapperToDB.map(order, preparedStatement);
            statements.add(preparedStatement);
            for (Product product : order.getProducts().keySet()) {
                PreparedStatement ps = connection.prepareStatement(SQL_add_payment);
                ps.setInt(1, order.getOrderId());
                ps.setString(2, product.getCode());
                ps.setDouble(3, order.getProducts().get(product));
                ps.setBoolean(4, order.getOrderStatus().ordinal() == 2);
                ps.setString(5, "Generated by Order DAO");
                statements.add(ps);
            }
            for (PreparedStatement ps : statements)
                ps.executeUpdate();
            connection.commit();
            /** Transaction finished */
        } catch (SQLException sqle) {
            result = false;
        } finally {
            MySQLDaoFactory.closeConnection(connection);
            return result;
        }
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
    public boolean closeOrder(Order order) throws IncorrectPropertyException, DataBaseConnectionException  {
        boolean result = false;
        Connection connection = MySQLDaoFactory.getConnection();
        try {
            /** Begin of transaction */
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("");
            result = preparedStatement.executeUpdate() > 0;
            connection.commit();
            /** Transaction finished */
        } catch (SQLException sqle) {
            result = false;
        } finally {
            MySQLDaoFactory.closeConnection(connection);
            return result;
        }
    }
}

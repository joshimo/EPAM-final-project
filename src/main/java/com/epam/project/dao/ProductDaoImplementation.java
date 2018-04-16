package com.epam.project.dao;

import com.epam.project.entities.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ProductDaoImplementation implements IProductDao {

    private Connection connection;

    public ProductDaoImplementation(Connection connection) {
        this.connection = connection;
    }

    /** Implementation of IProductDao interface methods */
    @Override
    public List<Product> findAllProductsInDB() throws Exception {
        List<Product> productList = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = Connector.sendRequest(statement,"SELECT * FROM stock;");
        while (resultSet.next()) {
            Product product = mapProductFromResultSet(resultSet);
            productList.add(product);
        }
        Connector.closeStatement(statement);
        resultSet.close();
        return productList;
    }

    @Override
    public Product findProductById(Integer id) throws Exception {
        Product product = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = Connector.sendRequest(statement,"SELECT * FROM stock WHERE PRODUCT_ID=" + id + ";");
        if (resultSet.next())
            product = mapProductFromResultSet(resultSet);
        resultSet.close();
        Connector.closeStatement(statement);
        return product;
    }

    @Override
    public boolean addProductToDB(Product product) throws Exception {
        String preparedRequest = "INSERT INTO `project`.`stock` (" +
                "`IS_AVAILABLE`, `PRODUCT_NAME_EN`, `PRODUCT_NAME_RU`, `PRODUCT_DESCRIPTION_EN`, `PRODUCT_DESCRIPTION_RU`, " +
                "`PRODUCT_COST`, `PRODUCT_QUANTITY`, `PRODUCT_UOM_EN`, `PRODUCT_UOM_RU`, `PRODUCT_NOTES_EN`, `PRODUCT_NOTES_RU`" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = createPreparedStatement(preparedRequest);
        mapper.map(product, preparedStatement);
        if (preparedStatement.executeUpdate() > 0)
            return true;
        return false;
    }

    @Override
    public boolean updateProductInDB(Product product) throws Exception {
        String preparedRequest = "UPDATE project.stock SET IS_AVAILABLE=?, PRODUCT_NAME_EN=?, PRODUCT_NAME_RU=?, " +
                          "PRODUCT_DESCRIPTION_EN=?, PRODUCT_DESCRIPTION_RU=?, PRODUCT_COST=?, PRODUCT_QUANTITY=?, " +
                          "PRODUCT_UOM_EN=?, PRODUCT_UOM_RU=?, PRODUCT_NOTES_EN=?, PRODUCT_NOTES_RU=? " +
                          "WHERE PRODUCT_ID=" + product.getId() + ";";
        PreparedStatement preparedStatement = createPreparedStatement(preparedRequest);
        mapper.map(product, preparedStatement);
        if (preparedStatement.executeUpdate() > 0)
            return true;
        return false;
    }

    @Override
    public boolean deleteProductFromDB(Integer id) throws Exception {
        String preparedRequest = "DELETE FROM project.stock WHERE PRODUCT_ID=?;";
        PreparedStatement preparedStatement = createPreparedStatement(preparedRequest);
        preparedStatement.setInt(1, id);
        if (preparedStatement.executeUpdate() > 0)
            return true;
        return false;
    }



    /** Private methods for serving methods implementing DAO interface */

    private interface Mapper {
        void map(Product product, PreparedStatement ps) throws SQLException;
    }

    Mapper mapper = (Product product, PreparedStatement preparedStatement) -> {
        preparedStatement.setBoolean(1, product.getAvailable());
        preparedStatement.setString(2, product.getNameEn());
        preparedStatement.setString(3, product.getNameRu());
        preparedStatement.setString(4, product.getDescriptionEn());
        preparedStatement.setString(5, product.getDescriptionRu());
        preparedStatement.setDouble(6, product.getCost());
        preparedStatement.setDouble(7, product.getQuantity());
        preparedStatement.setString(8, product.getUomEn());
        preparedStatement.setString(9, product.getUomRu());
        preparedStatement.setString(10, product.getNotesEn());
        preparedStatement.setString(11, product.getNotesRu());
    };

    private Product mapProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("PRODUCT_ID"));
        product.setAvailable(resultSet.getBoolean("IS_AVAILABLE"));
        product.setNameEn(resultSet.getString("PRODUCT_NAME_EN"));
        product.setNameRu(resultSet.getString("PRODUCT_NAME_RU"));
        product.setDescriptionEn(resultSet.getString("PRODUCT_DESCRIPTION_EN"));
        product.setDescriptionRu(resultSet.getString("PRODUCT_DESCRIPTION_RU"));
        product.setCost(resultSet.getDouble("PRODUCT_COST"));
        product.setQuantity(resultSet.getDouble("PRODUCT_QUANTITY"));
        product.setUomEn(resultSet.getString("PRODUCT_UOM_EN"));
        product.setUomRu(resultSet.getString("PRODUCT_UOM_RU"));
        product.setNotesEn(resultSet.getString("PRODUCT_NOTES_EN"));
        product.setNotesRu(resultSet.getString("PRODUCT_NOTES_RU"));
        return product;
    }

    private PreparedStatement createPreparedStatement(String preparedRequest) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(preparedRequest);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return ps;
    }
}
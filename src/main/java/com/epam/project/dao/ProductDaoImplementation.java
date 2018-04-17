package com.epam.project.dao;

import com.epam.project.entities.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ProductDaoImplementation implements IProductDao {

    private static String selectAll = "SELECT * FROM stock;";
    private static String selectById = "SELECT * FROM stock WHERE product_id=?;";
    private static String selectByCode = "SELECT * FROM stock WHERE product_code=?;";
    private static String addNewProduct = "INSERT INTO `project`.`stock` (" +
            "product_code, is_available, product_name_en, product_name_ru, product_description_en, product_description_ru, " +
            "product_cost, product_quantity, product_uom_en, product_uom_ru, product_notes_en, product_notes_ru" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    private static String updateProduct = "UPDATE project.stock SET product_code=?, " +
            "is_available=?, product_name_en=?, product_name_ru=?, product_description_en=?, product_description_ru=?, " +
            "product_cost=?, product_quantity=?, product_uom_en=?, product_uom_ru=?, " +
            "product_notes_en=?, product_notes_ru=? WHERE product_id=?";
    private static String deleteProduct = "DELETE FROM project.stock WHERE PRODUCT_ID=?;";

    private Connection connection;

    public ProductDaoImplementation(Connection connection) {
        this.connection = connection;
    }

    /** Implementation of IProductDao interface methods */
    @Override
    public List<Product> findAllProductsInDB() throws Exception {
        List<Product> productList = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = Connector.sendRequest(statement, selectAll);
        while (resultSet.next()) {
            Product product = mapProductFromResultSet(resultSet);
            productList.add(product);
        }
        resultSet.close();
        Connector.closeStatement(statement);
        return productList;
    }

    @Override
    public Product findProductById(Integer id) throws Exception {
        Product product = null;
        PreparedStatement preparedStatement = createPreparedStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            product = mapProductFromResultSet(resultSet);
        resultSet.close();
        Connector.closeStatement(preparedStatement);
        return product;
    }

    @Override
    public Product findProductByCode(String code) throws Exception {
        Product product = null;
        PreparedStatement preparedStatement = createPreparedStatement(selectById);
        preparedStatement.setString(1, code);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            product = mapProductFromResultSet(resultSet);
        resultSet.close();
        Connector.closeStatement(preparedStatement);
        return product;
    }

    @Override
    public boolean addProductToDB(Product product) throws Exception {
        PreparedStatement preparedStatement = createPreparedStatement(addNewProduct);
        mapper.map(product, preparedStatement);
        boolean result = preparedStatement.executeUpdate() > 0;
        Connector.closeStatement(preparedStatement);
        return result;
    }

    @Override
    public boolean updateProductInDB(Product product) throws Exception {
        PreparedStatement preparedStatement = createPreparedStatement(updateProduct);
        mapper.map(product, preparedStatement);
        preparedStatement.setInt(13, product.getId());
        boolean result = preparedStatement.executeUpdate() > 0;
        Connector.closeStatement(preparedStatement);
        return result;
    }

    @Override
    public boolean deleteProductFromDB(Integer id) throws Exception {
        PreparedStatement preparedStatement = createPreparedStatement(deleteProduct);
        preparedStatement.setInt(1, id);
        boolean result = preparedStatement.executeUpdate() > 0;
        Connector.closeStatement(preparedStatement);
        return result;
    }

    @Override
    public boolean deleteProductFromDB(String productCode) throws Exception {
        PreparedStatement preparedStatement = createPreparedStatement(deleteProduct);
        preparedStatement.setString(1, productCode);
        boolean result = preparedStatement.executeUpdate() > 0;
        Connector.closeStatement(preparedStatement);
        return result;
    }


    /** Private methods for serving methods implementing DAO interface */

    private interface Mapper {
        void map(Product product, PreparedStatement ps) throws SQLException;
    }

    Mapper mapper = (Product product, PreparedStatement preparedStatement) -> {
        preparedStatement.setString(1, product.getCode());
        preparedStatement.setBoolean(2, product.getAvailable());
        preparedStatement.setString(3, product.getNameEn());
        preparedStatement.setString(4, product.getNameRu());
        preparedStatement.setString(5, product.getDescriptionEn());
        preparedStatement.setString(6, product.getDescriptionRu());
        preparedStatement.setDouble(7, product.getCost());
        preparedStatement.setDouble(8, product.getQuantity());
        preparedStatement.setString(9, product.getUomEn());
        preparedStatement.setString(10, product.getUomRu());
        preparedStatement.setString(11, product.getNotesEn());
        preparedStatement.setString(12, product.getNotesRu());
    };

    private Product mapProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("product_id"));
        product.setCode(resultSet.getString("product_code"));
        product.setAvailable(resultSet.getBoolean("is_available"));
        product.setNameEn(resultSet.getString("product_name_en"));
        product.setNameRu(resultSet.getString("product_name_ru"));
        product.setDescriptionEn(resultSet.getString("product_description_en"));
        product.setDescriptionRu(resultSet.getString("product_description_ru"));
        product.setCost(resultSet.getDouble("product_cost"));
        product.setQuantity(resultSet.getDouble("product_quantity"));
        product.setUomEn(resultSet.getString("product_uom_en"));
        product.setUomRu(resultSet.getString("product_uom_ru"));
        product.setNotesEn(resultSet.getString("product_notes_en"));
        product.setNotesRu(resultSet.getString("product_notes_ru"));
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
package com.epam.project.dao;

import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ProductDaoImpl extends GenericAbstractDao<Product> implements IProductDao {

    private Connection connection;
    private static String SQL_selectAll = "SELECT * FROM stock;";
    private static String SQL_selectById = "SELECT * FROM stock WHERE product_id=?;";
    private static String SQL_selectByCode = "SELECT * FROM stock WHERE product_code=?;";
    private static String SQL_addNewProduct = "INSERT INTO project.stock (product_code, is_available, " +
            "product_name_en, product_name_ru, product_description_en, product_description_ru, " +
            "product_cost, product_quantity, reserved_quantity, product_uom_en, product_uom_ru, product_notes_en, product_notes_ru" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static String SQL_updateProduct = "UPDATE project.stock SET product_code=?, is_available=?, " +
            "product_name_en=?, product_name_ru=?, product_description_en=?, product_description_ru=?, " +
            "product_cost=?, product_quantity=?, reserved_quantity=?, product_uom_en=?, product_uom_ru=?, " +
            "product_notes_en=?, product_notes_ru=? WHERE product_id=?;";
    private static String SQL_deleteProductById = "DELETE FROM project.stock WHERE product_id=?;";
    private static String SQL_deleteProductByCode = "DELETE FROM project.stock WHERE product_code=?;";

    /** Private methods for serving methods implementing DAO interface */

    private Mapper<Product, PreparedStatement> mapperToDB = (Product product, PreparedStatement preparedStatement) -> {
        preparedStatement.setString(1, product.getCode());
        preparedStatement.setBoolean(2, product.getAvailable());
        preparedStatement.setString(3, product.getNameEn());
        preparedStatement.setString(4, product.getNameRu());
        preparedStatement.setString(5, product.getDescriptionEn());
        preparedStatement.setString(6, product.getDescriptionRu());
        preparedStatement.setDouble(7, product.getCost());
        preparedStatement.setDouble(8, product.getQuantity());
        preparedStatement.setDouble(9, product.getReservedQuantity());
        preparedStatement.setString(10, product.getUomEn());
        preparedStatement.setString(11, product.getUomRu());
        preparedStatement.setString(12, product.getNotesEn());
        preparedStatement.setString(13, product.getNotesRu());
    };

    private Mapper<ResultSet, Product> mapperFromDB = (ResultSet resultSet, Product product) -> {
        product.setId(resultSet.getInt("product_id"));
        product.setCode(resultSet.getString("product_code"));
        product.setAvailable(resultSet.getBoolean("is_available"));
        product.setNameEn(resultSet.getString("product_name_en"));
        product.setNameRu(resultSet.getString("product_name_ru"));
        product.setDescriptionEn(resultSet.getString("product_description_en"));
        product.setDescriptionRu(resultSet.getString("product_description_ru"));
        product.setCost(resultSet.getDouble("product_cost"));
        product.setQuantity(resultSet.getDouble("product_quantity"));
        product.setReservedQuantity(resultSet.getDouble("reserved_quantity"));
        product.setUomEn(resultSet.getString("product_uom_en"));
        product.setUomRu(resultSet.getString("product_uom_ru"));
        product.setNotesEn(resultSet.getString("product_notes_en"));
        product.setNotesRu(resultSet.getString("product_notes_ru"));
    };

    public ProductDaoImpl(Connection connection) {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        this.connection = connection;
    }

    @Override
    public List<Product> findAllProductsInDB() throws DataNotFoundException {
        List<Product> products = findAll(connection, Product.class, SQL_selectAll);
        return products;
    }

    @Override
    public Product findProductById(Integer id) throws DataNotFoundException {
        Product product = findBy(connection, Product.class, SQL_selectById, id);
        return product;
    }

    @Override
    public Product findProductByCode(String code) throws DataNotFoundException {
        Product product = findBy(connection, Product.class, SQL_selectByCode, code);
        return product;
    }

    @Override
    public boolean addProductToDB(Product product) {
        boolean result = addToDB(connection, product, SQL_addNewProduct);
        return result;
    }

    @Override
    public boolean updateProductInDB(Product product) {
        Integer id = product.getId();
        boolean result = updateInDB(connection, product, SQL_updateProduct, 14, id);
        return result;
    }

    @Override
    public boolean deleteProductFromDB(Integer id) {
        boolean result = deleteFromDB(connection, SQL_deleteProductById, id);
        return result;
    }

    @Override
    public boolean deleteProductFromDB(String code) {
        boolean result = deleteFromDB(connection, SQL_deleteProductByCode, code);
        return result;
    }
}
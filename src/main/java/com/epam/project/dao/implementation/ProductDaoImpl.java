package com.epam.project.dao.implementation;

import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.IProductDao;
import com.epam.project.dao.Mapper;
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
    private static String SQL_select_base = "SELECT * FROM products ";
    private static String SQL_selectAll = "SELECT * FROM products;";
    private static String SQL_selectById = "SELECT * FROM products WHERE product_id=?;";
    private static String SQL_selectByCode = "SELECT * FROM products WHERE product_code=?;";
    private static String SQL_addNewProduct = "INSERT INTO project.products (product_code, is_available, " +
            "product_name_en, product_name_ru, product_description_en, product_description_ru, " +
            "product_cost, product_quantity, reserved_quantity, product_uom_en, product_uom_ru, product_notes_en, product_notes_ru" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static String SQL_updateProduct = "UPDATE project.products SET product_code=?, is_available=?, " +
            "product_name_en=?, product_name_ru=?, product_description_en=?, product_description_ru=?, " +
            "product_cost=?, product_quantity=?, reserved_quantity=?, product_uom_en=?, product_uom_ru=?, " +
            "product_notes_en=?, product_notes_ru=? WHERE product_id=?;";
    private static String SQL_deleteProductById = "DELETE FROM project.products WHERE product_id=?;";
    private static String SQL_deleteProductByCode = "DELETE FROM project.products WHERE product_code=?;";

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
    public Integer calculateProductNumber() throws DataNotFoundException {
        return calculateRowCounts(connection, "products");
    }

    @Override
    public List<Product> findAllProductsInDB() throws DataNotFoundException {
        List<Product> products = findAll(connection, Product.class, SQL_selectAll);
        return products;
    }

    @Override
    public List<Product> findProductsInDB(Integer first, Integer offset) throws DataNotFoundException {
        return findAllFromTo(connection, Product.class, first, offset, SQL_select_base);
    }

    @Override
    public Product findProductById(Integer id) throws DataNotFoundException {
        return findBy(connection, Product.class, SQL_selectById, id);
    }

    @Override
    public Product findProductByCode(String code) throws DataNotFoundException {
        return findBy(connection, Product.class, SQL_selectByCode, code);
    }

    @Override
    public boolean addProductToDB(Product product) {
        return addToDB(connection, product, SQL_addNewProduct);
    }

    @Override
    public boolean updateProductInDB(Product product) {
        Integer id = product.getId();
        return updateInDB(connection, product, SQL_updateProduct, 14, id);
    }

    @Override
    public boolean deleteProductFromDB(Integer id) {
        return deleteFromDB(connection, SQL_deleteProductById, id);
    }

    @Override
    public boolean deleteProductFromDB(String code) {
        return deleteFromDB(connection, SQL_deleteProductByCode, code);
    }
}
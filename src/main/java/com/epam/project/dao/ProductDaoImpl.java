package com.epam.project.dao;

import com.epam.project.entities.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ProductDaoImpl extends GenericAbstractDao<Product> implements IProductDao {

    private static String SQL_selectAll = "SELECT * FROM stock;";
    private static String SQL_selectById = "SELECT * FROM stock WHERE product_id=?;";
    private static String SQL_selectByCode = "SELECT * FROM stock WHERE product_code=?;";
    private static String SQL_addNewProduct = "INSERT INTO project.stock (product_code, is_available, " +
            "product_name_en, product_name_ru, product_description_en, product_description_ru, " +
            "product_cost, product_quantity, product_uom_en, product_uom_ru, product_notes_en, product_notes_ru" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    private static String SQL_updateProduct = "UPDATE project.stock SET product_code=?, is_available=?, " +
            "product_name_en=?, product_name_ru=?, product_description_en=?, product_description_ru=?, " +
            "product_cost=?, product_quantity=?, product_uom_en=?, product_uom_ru=?, " +
            "product_notes_en=?, product_notes_ru=? WHERE product_code=?;";
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
        preparedStatement.setString(9, product.getUomEn());
        preparedStatement.setString(10, product.getUomRu());
        preparedStatement.setString(11, product.getNotesEn());
        preparedStatement.setString(12, product.getNotesRu());
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
        product.setUomEn(resultSet.getString("product_uom_en"));
        product.setUomRu(resultSet.getString("product_uom_ru"));
        product.setNotesEn(resultSet.getString("product_notes_en"));
        product.setNotesRu(resultSet.getString("product_notes_ru"));
    };

    public ProductDaoImpl() {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public List<Product> findAllProductsInDB() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        List<Product> products = findAll(Product.class, SQL_selectAll);
        return products;
    }

    @Override
    public Product findProductById(Integer id) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Product product = findBy(Product.class, SQL_selectById, id);
        return product;
    }

    @Override
    public Product findProductByCode(String code) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        Product product = findBy(Product.class, SQL_selectByCode, code);
        return product;
    }

    @Override
    public boolean addProductToDB(Product product) throws IncorrectPropertyException, DataBaseConnectionException {
        return addToDB(product, SQL_addNewProduct);
    }

    @Override
    public boolean updateProductInDB(Product product) throws IncorrectPropertyException, DataBaseConnectionException {
        String code = product.getCode();
        return updateInDB(product, SQL_updateProduct, 13, code);
    }

    @Override
    public boolean deleteProductFromDB(Integer id) throws IncorrectPropertyException, DataBaseConnectionException {
        return deleteFromDB(SQL_deleteProductById, id);
    }

    @Override
    public boolean deleteProductFromDB(String code) throws IncorrectPropertyException, DataBaseConnectionException {
        return deleteFromDB(SQL_deleteProductByCode, code);
    }
}
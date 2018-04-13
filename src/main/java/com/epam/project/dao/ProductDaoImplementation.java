package com.epam.project.dao;

import com.epam.project.entities.Product;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.util.List;

public class ProductDaoImplementation implements IProductDao {

    private Connection connection;

    public ProductDaoImplementation(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> retrieveAllProductsFromDB() throws Exception {
        throw new Exception("Operation not supported yet!");
    }

    @Override
    public Product retrieveProductById(Integer id) throws Exception {
        throw new Exception("Operation not supported yet!");
    }

    @Override
    public boolean addProductToDB(Product product) throws Exception {
        throw new Exception("Operation not supported yet!");
    }

    @Override
    public boolean updateProductInDB(Product product) throws Exception {
        throw new Exception("Operation not supported yet!");
    }

    @Override
    public boolean deleteProductFromDB(Product product) throws Exception {
        throw new Exception("Operation not supported yet!");
    }
}

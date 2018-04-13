package com.epam.project.dao;

import com.epam.project.entities.Product;

import java.util.List;

public interface IProductDao {

    List<Product> retrieveAllProductsFromDB() throws Exception;

    Product retrieveProductById(Integer id) throws Exception;

    boolean addProductToDB(Product product) throws Exception;

    boolean updateProductInDB(Product product) throws Exception;

    boolean deleteProductFromDB(Product product) throws Exception;

}

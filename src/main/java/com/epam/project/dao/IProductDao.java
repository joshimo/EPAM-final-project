package com.epam.project.dao;

import com.epam.project.entities.Product;

import java.util.List;

public interface IProductDao {

    List<Product> findAllProductsInDB() throws Exception;

    Product findProductById(Integer id) throws Exception;

    Product findProductByCode(String code) throws Exception;

    boolean addProductToDB(Product product) throws Exception;

    boolean updateProductInDB(Product product) throws Exception;

    boolean deleteProductFromDB(Integer id) throws Exception;

    boolean deleteProductFromDB(String code) throws Exception;

}

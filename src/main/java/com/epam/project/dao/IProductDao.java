package com.epam.project.dao;

import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.util.List;

public interface IProductDao {

    List<Product> findAllProductsInDB() throws IncorrectPropertyException, DataBaseConnectionException,
            DataNotFoundException;

    Product findProductById(Integer id) throws IncorrectPropertyException, DataBaseConnectionException,
            DataNotFoundException;

    Product findProductByCode(String code) throws IncorrectPropertyException, DataBaseConnectionException,
            DataNotFoundException;

    boolean addProductToDB(Product product) throws IncorrectPropertyException, DataBaseConnectionException;

    boolean updateProductInDB(Product product) throws IncorrectPropertyException, DataBaseConnectionException;

    boolean deleteProductFromDB(Integer id) throws IncorrectPropertyException, DataBaseConnectionException;

    boolean deleteProductFromDB(String code) throws IncorrectPropertyException, DataBaseConnectionException;

}
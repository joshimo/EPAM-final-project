package com.epam.project.dao;

import com.epam.project.entities.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataDuplicationException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.util.List;

public interface IProductDao {

    <T extends Exception> List<Product> findAllProductsInDB() throws T;

    <T extends Exception> Product findProductById(Integer id) throws T;

    <T extends Exception> Product findProductByCode(String code) throws T;

    <T extends Exception> boolean addProductToDB(Product product) throws T;

    <T extends Exception> boolean updateProductInDB(Product product) throws T;

    <T extends Exception> boolean deleteProductFromDB(Integer id) throws T;

    <T extends Exception> boolean deleteProductFromDB(String code) throws T;

}

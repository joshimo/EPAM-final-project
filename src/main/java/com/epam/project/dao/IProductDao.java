package com.epam.project.dao;

import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.util.List;

/**
 * CRUD operations interface for Product entity
 */
public interface IProductDao {

    /**
     * Calculates total product number available in DB
     * @return count of products in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateProductNumber() throws DataNotFoundException;

    /**
     * Finds all products in DB
     * @return List of all products in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Product> findAllProductsInDB() throws DataNotFoundException;

    /**
     * Finds products in DB from
     * @param first first row number
     * @param offset offset
     * @return List of all products in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Product> findProductsInDB(Integer first, Integer offset) throws DataNotFoundException;

    /**
     * Finds product in DB by id number
     * @param id - product id number
     * @return product by id number
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Product findProductById(Integer id) throws DataNotFoundException;

    /**
     * Finds product in DB by product ordering code
     * @param code - product ordering code
     * @return product by product ordering code
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Product findProductByCode(String code) throws DataNotFoundException;

    /**
     * Adds new product to database
     * @param product - product to add in DB
     * @return true if operation success and false if fails
     */
    boolean addProductToDB(Product product);

    /**
     * Updates existent product to database
     * @param product - product to update in DB
     * @return true if operation success and false if fails
     */
    boolean updateProductInDB(Product product);

    /**
     * Deletes existent product from database by product id number
     * @param id - product id number
     * @return true if operation success and false if fails
     */
    boolean deleteProductFromDB(Integer id);

    /**
     * Deletes existent product from database by product ordering code
     * @param code - product ordering code
     * @return true if operation success and false if fails
     */
    boolean deleteProductFromDB(String code);
}
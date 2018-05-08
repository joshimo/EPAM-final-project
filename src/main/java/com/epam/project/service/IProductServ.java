package com.epam.project.service;

import com.epam.project.domain.Product;
import com.epam.project.exceptions.ProductServiceException;

import java.util.List;

public interface IProductServ {

    @Button
    List<Product> findAllProducts() throws ProductServiceException;

    Product findProductByCode(String code) throws ProductServiceException;

    @Button
    boolean addProduct(Product product);

    @Button
    boolean updateProduct(Product product);

    boolean deleteProduct(Product product);

    @Button
    boolean deleteProduct(String code);
}

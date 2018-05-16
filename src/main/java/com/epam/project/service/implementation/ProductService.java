package com.epam.project.service.implementation;

import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IProductDao;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.*;
import com.epam.project.service.Button;
import com.epam.project.service.IProductServ;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ProductService implements IProductServ {

    private static final Logger log = Logger.getLogger(ProductService.class);
    private static final DataBaseSelector source = DataBaseSelector.MY_SQL;

    private static DaoFactory daoFactory;
    private static IProductDao productDao;

    static {
        try {
            daoFactory = DaoFactory.getDaoFactory(source);
            productDao = daoFactory.getProductDao();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
        }
    }

    /** User validation method to check user before storing in DB */

    public boolean validateProductData(Product product) {
        return !(product.getCode() == null || product.getCode().isEmpty()
                || product.getNameEn() == null || product.getNameEn().isEmpty()
                || product.getNameRu() == null || product.getNameRu().isEmpty()
                || product.getUomEn() == null || product.getUomEn().isEmpty()
                || product.getUomRu() == null || product.getUomRu().isEmpty());
    }

    /** Data access and storing methods */

    @Button
    public List<Product> findAllProducts() throws ProductServiceException {
        List<Product> products = new LinkedList<>();
        try {
            daoFactory.open();
            productDao = daoFactory.getProductDao();
            products = productDao.findAllProductsInDB();
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new ProductServiceException();
        }
        return products;
    }

    public Product findProductByCode(String code) throws ProductServiceException {
        Product product = new Product();
        try {
            daoFactory.open();
            productDao = daoFactory.getProductDao();
            product = productDao.findProductByCode(code);
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new ProductServiceException();
        }
        return product;
    }

    @Button
    public boolean addProduct(Product product) {
        boolean result;
        try {
            daoFactory.beginTransaction();
            productDao = daoFactory.getProductDao();
            result = validateProductData(product) && productDao.addProductToDB(product);
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    @Button
    public boolean updateProduct(Product product) {
        boolean result;
        try {
            daoFactory.beginTransaction();
            productDao = daoFactory.getProductDao();
            result = validateProductData(product) && productDao.updateProductInDB(product);
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    public boolean updateProducts(List<Product> products) {
        try {
            daoFactory.beginTransaction();
            productDao = daoFactory.getProductDao();
            for (Product product : products)
                if (!productDao.updateProductInDB(product)) {
                    daoFactory.rollbackTransaction();
                    return false;
                }
            daoFactory.commitTransaction();
            return true;
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
    }

    public boolean deleteProduct(Product product) {
        boolean result;
        try {
            daoFactory.beginTransaction();
            productDao = daoFactory.getProductDao();
            result = productDao.deleteProductFromDB(product.getCode());
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    @Button
    public boolean deleteProduct(String code) {
        boolean result;
        try {
            daoFactory.beginTransaction();
            productDao = daoFactory.getProductDao();
            result = productDao.deleteProductFromDB(code);
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }
}
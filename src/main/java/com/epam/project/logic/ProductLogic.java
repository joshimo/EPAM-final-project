package com.epam.project.logic;

import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IProductDao;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ProductLogic {

    private static final Logger log = Logger.getLogger(ProductLogic.class);
    private static final DataBaseSelector source = DataBaseSelector.MY_SQL;

    /** User validation method to check user before storing in DB */

    public static boolean validateProductData(Product product) {
        return !(product.getCode() == null || product.getCode().isEmpty()
                || product.getNameEn() == null || product.getNameEn().isEmpty()
                || product.getNameRu() == null || product.getNameRu().isEmpty()
                || product.getUomEn() == null || product.getUomEn().isEmpty()
                || product.getUomRu() == null || product.getUomRu().isEmpty());
    }

    /** Data access and storing methods */

    public static List<Product> findAllProducts() {
        List<Product> products = new LinkedList<>();
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(source);
            IProductDao productDao = daoFactory.getProductDao();
            products = productDao.findAllProductsInDB();
            daoFactory.closeConnection();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException | DataNotFoundException ex) {
            log.error(ex);
        }
        return products;
    }

    public static Product findProductByCode(String code) {
        Product product = new Product();
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(source);
            IProductDao productDao = daoFactory.getProductDao();
            product = productDao.findProductByCode(code);
            daoFactory.closeConnection();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException | DataNotFoundException ex) {
            log.error(ex);
        }
        return product;
    }

    public static boolean addProduct(Product product) {
        boolean result;
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(source);
            IProductDao productDao = daoFactory.getProductDao();
            result = validateProductData(product) && productDao.addProductToDB(product);
            daoFactory.closeConnection();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    public static boolean updateProduct(Product product) {
        boolean result;
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(source);
            IProductDao productDao = daoFactory.getProductDao();
            result = validateProductData(product) && productDao.updateProductInDB(product);
            daoFactory.closeConnection();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    //ToDo: Unsafe operation. This realisation shall be replaced by transaction
    public static boolean updateProducts(List<Product> products) {
        return true;
    }

    public static boolean deleteProduct(Product product) {
        boolean result;
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(source);
            IProductDao productDao = daoFactory.getProductDao();
            result = productDao.deleteProductFromDB(product.getCode());
            daoFactory.closeConnection();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    public static boolean deleteProduct(String code) {
        boolean result;
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(source);
            IProductDao productDao = daoFactory.getProductDao();
            result = productDao.deleteProductFromDB(code);
            daoFactory.closeConnection();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }
}
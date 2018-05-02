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

import java.util.List;

public class ProductLogic {

    private static IProductDao productDao;

    private static final Logger log = Logger.getLogger(ProductLogic.class);

    static {
        try {
            productDao = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL).getProductDao();
        } catch (IncorrectPropertyException ipe) {
            log.error(ipe);
        } catch (DataBaseNotSupportedException dbnse) {
            log.error(dbnse);
        }
    }

    /** User validation method to check user before storing in DB */

    public static boolean validateProductData(Product product) {
        return !(product.getCode() == null || product.getCode().isEmpty()
                || product.getNameEn() == null || product.getNameEn().isEmpty()
                || product.getNameRu() == null || product.getNameRu().isEmpty()
                || product.getUomEn() == null || product.getUomEn().isEmpty()
                || product.getUomRu() == null || product.getUomRu().isEmpty());
    }

    /** Data access and storing methods */

    public static List<Product> findAllProducts() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        return productDao.findAllProductsInDB();
    }

    public static Product findProductByCode(String code) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        return productDao.findProductByCode(code);
    }

    public static boolean addProduct(Product product) throws IncorrectPropertyException, DataBaseConnectionException  {
        if (validateProductData(product))
            return productDao.addProductToDB(product);
        return false;
    }

    public static boolean updateProduct(Product product) throws IncorrectPropertyException, DataBaseConnectionException  {
        if (validateProductData(product))
            return productDao.updateProductInDB(product);
        return false;
    }

    //ToDo: Unsafe operation. This realisation shall be replaced by transaction
    public static boolean updateProducts(List<Product> products) throws IncorrectPropertyException, DataBaseConnectionException   {
        for (Product product : products)
            if (!productDao.updateProductInDB(product))
                return false;
        return true;
    }

    public static boolean deleteProduct(Product product) throws IncorrectPropertyException, DataBaseConnectionException  {
        return productDao.deleteProductFromDB(product.getCode());
    }

    public static boolean deleteProduct(String code) throws IncorrectPropertyException, DataBaseConnectionException  {
        return productDao.deleteProductFromDB(code);
    }
}
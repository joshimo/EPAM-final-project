package com.epam.project.domain;

import java.util.HashMap;
import java.util.Map;

/** Data transfer object */
public class UserCartView {

    private String userName;
    private String orderNotes;
    private Map<Product, Double> products;
    private Double totalCost = 0d;

    public UserCartView() {
        products = new HashMap<>();
    }

    public UserCartView(String userName) {
        this.userName = userName;
        products = new HashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public Map<Product, Double> getProducts() {
        return products;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Integer getSize() {
        return products.size();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public void setProducts(Map<Product, Double> products) {
        this.products = products;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void addProduct(Product product, Double quantity) {
        if (product != null && quantity != null)
            products.put(product, quantity);

    }
}

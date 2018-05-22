package com.epam.project.domain;

import java.util.HashMap;
import java.util.Map;

/** Data transfer object */
public class UserCart {

    private String userName;
    private String orderNotes;
    private Map<String, Double> products;
    private Double totalCost = 0d;

    public UserCart(String userName) {
        this.userName = userName;
        products = new HashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public Map<String, Double> getProducts() {
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

    public void setProducts(Map<String, Double> products) {
        this.products = products;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void addProduct(String productCode, Double quantity) {
        if (products.containsKey(productCode)) {
            Double newQuantity = products.get(productCode) + quantity;
            products.replace(productCode, newQuantity);
        }
        else
            products.put(productCode, quantity);
    }

    public void removeProduct(String productCode) {
        products.remove(productCode);
    }

    public void removeAll() {
        products = new HashMap<>();
    }

    @Override
    public String toString() {
        return "UserCart{" +
                "userName='" + userName + '\'' +
                ", orderNotes='" + orderNotes + '\'' +
                ", products=" + products +
                '}';
    }
}

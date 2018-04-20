package com.epam.project.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/** 'Order' Enity is mapped both to project.orders and project.payments tables*/
public class Order {

    private Integer orderId;
    private String userName;
    private Integer userId;
    private OrderStatus orderStatus;
    private Timestamp orderDate;
    private Map<Product, Double> products;
    private Map<String, Double> prod;
    private String notes;

    public Order() {
        products = new HashMap<>();
        prod = new HashMap<>();
    }

    public Order(Integer id) {
        this();
        orderId = id;
    }
    /** Getters */
    public Integer getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public Map<Product, Double> getProducts() {
        return products;
    }

    public String getNotes() {
        return notes;
    }

    /** Setters */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public void setProducts(Map<Product, Double> products) {
        this.products = products;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /** Methods to add a product to order */

    public void addProduct(Product product, Double quantity) {
        products.put(product, quantity);
    }

    public void addProduct(String productCode, Double quantity) {
        prod.put(productCode, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId);
    }

    @Override
    public int hashCode() {
        return orderId.hashCode();
    }

    @Override
    public String toString() {
        int num = 0;
        StringBuilder sb = new StringBuilder("\nOrder Id = ").append(orderId);
        sb.append("\nUser name: ").append(userName);
        sb.append("\nStatus: ").append(orderStatus.name());
        sb.append("\nDate: ").append(orderDate);
        sb.append("\nNotes: ").append(notes);
        sb.append("\nOrder details:").append("\n*********************************************************************");
        for (Map.Entry<Product, Double> entry : products.entrySet()) {
            num ++;
            Product product = entry.getKey();
            Double quantity = entry.getValue();
            sb.append("\n").append(num).append(". ").append(product.getCode()).append(": ")
                    .append(product.getNameEn()).append(" = ").append(quantity).append(product.getUomEn()).append(";");
        }
        for (Map.Entry<String, Double> entry : prod.entrySet()) {
            num ++;
            String code = entry.getKey();
            Double quantity = entry.getValue();
            sb.append("\n").append(num).append(". ").append(code).append(": ").append(quantity).append(";");
        }
        sb.append("\n*********************************************************************");
        sb.append("\n------------------------------------------------------------------------------------------------");
        return  sb.toString();
    }
}
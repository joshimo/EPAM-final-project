package com.epam.project.entities;

import java.sql.Timestamp;
import java.util.Map;

/** 'Order' Enity is mapped both to project.orders and project.payments tables*/
public class Order {
    private Integer orderId;
    private Integer userId;
    private Timestamp orderDate;
    private Boolean isApproved;
    private Boolean isCancelled;
    private Map<Product, Double> products;
    private Map<Integer, Double> productsAlt;
    private String notes;

    public Order() {
    }

    /** Getters */
    public Integer getOrderId() {
        return orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public Boolean getCancelled() {
        return isCancelled;
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public void setProducts(Map<Product, Double> products) {
        this.products = products;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
}
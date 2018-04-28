package com.epam.project.domain;

import java.sql.Timestamp;

/** 'Order' Enity is mapped to project.orders table */
@Deprecated
public class Order {

    private Integer orderId;
    private Long orderCode;
    private String userName;
    private OrderStatus status;
    private Timestamp date;
    private String orderNotes;

    public Order() {
    }

    /** Getters */
    public Integer getOrderId() {
        return orderId;
    }

    public Long getOrderCode() {
        return orderCode;
    }

    public String getUserName() {
        return userName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    /** Setters */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrderCode(Long orderCode) {
        this.orderCode = orderCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderCode.equals(order.orderCode);
    }

    @Override
    public int hashCode() {
        return orderCode.hashCode();
    }

    @Override
    public String toString() {
        return  "\nOrder ID = " + orderId +
                "\nOrder code: " + orderCode +
                "\nUser name: " + userName +
                "\nStatus: " + status +
                "\nDate: " + date +
                "\nNotes: " + orderNotes +
                "\n---------------------------------------------------------------------------------------------------";
    }
}
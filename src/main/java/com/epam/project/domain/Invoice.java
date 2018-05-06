package com.epam.project.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Invoice {

    private Integer orderId;
    private Long orderCode;
    private String userName;
    private OrderStatus status;
    private Timestamp date;
    private String orderNotes;
    private Map<String, Payment> payments;
    private Map<String, Product> products;

    public Invoice() {
        payments = new HashMap<>();
        products = new HashMap<>();
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

    public Map<String, Payment> getPayments() {
        return payments;
    }

    public Map<String, Product> getProducts() {
        return products;
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


    public void setPayments(Map<String, Payment> payments) {
        this.payments = payments;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    /** Add methods for storing payments and products to hashMap */

    @Deprecated
    public void addProduct(String productCode, Product product) {
        products.put(productCode, product);
    }

    public void addProduct(Product product) {
        products.put(product.getCode(), product);
    }

    @Deprecated
    public void addPayment(String productCode, Payment payment) {
        payments.put(productCode, payment);
    }

    public void addPayment(Payment payment) {
        payments.put(payment.getProductCode(), payment);
    }

    public void removePaymentAndProduct(String productCode) {
        payments.remove(productCode);
        products.remove(productCode);
    }

    @Override
    public String toString() {
        int num = 0;
        StringBuilder sb = new StringBuilder(String.format("%n%-15s", "Order Id =")).append(orderId);
        sb.append(String.format("%n%-15s", "Order code:")).append(orderCode);
        sb.append(String.format("%n%-15s", "User name:")).append(userName);
        sb.append(String.format("%n%-15s", "Order status:")).append(status);
        sb.append(String.format("%n%-15s", "Order date:")).append(date);
        sb.append(String.format("%n%-15s", "Order notes:")).append(orderNotes);
        sb.append(String.format("%n%12s", "Invoice:"));
        sb.append("\n************************************************************************************************");
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            num ++;
            String productCode = entry.getKey();
            Product product = entry.getValue();
            Payment payment = payments.get(productCode);
            sb.append(String.format("%n%-4s",num)).append(String.format("%-8s", product.getCode()));
            sb.append(String.format("%-48s", product.getNameEn())).append(String.format("%6.2f", payment.getQuantity()));
            sb.append(String.format("%-6s", product.getUomEn()));
            sb.append("Cost: ").append(String.format("%.2f", payment.getPaymentValue()));
        }
        sb.append("\n************************************************************************************************");
        sb.append("\n------------------------------------------------------------------------------------------------");
        return sb.toString();
    }
}

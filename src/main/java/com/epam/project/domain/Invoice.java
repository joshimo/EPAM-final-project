package com.epam.project.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/** Invoice entity mapped to invoices table */
public class Invoice implements Serializable {

    private Integer invoiceId;
    private Long invoiceCode;
    private String userName;
    private Boolean isPaid;
    private InvoiceStatus status;
    private Timestamp date;
    private String invoiceNotes;
    private Double cost = 0d;
    private Map<String, Payment> payments;
    private Map<String, Product> products;


    public Invoice() {
        payments = new HashMap<>();
        products = new HashMap<>();
    }

    /** Getters */

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public Long getInvoiceCode() {
        return invoiceCode;
    }

    public String getUserName() {
        return userName;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getInvoiceNotes() {
        return invoiceNotes;
    }

    public Map<String, Payment> getPayments() {
        return payments;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public Double getCost() {
        return cost;
    }

    /** Setters */

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setInvoiceCode(Long invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setInvoiceNotes(String invoiceNotes) {
        this.invoiceNotes = invoiceNotes;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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
        Product product = products.get(payment.getProductCode());
        payment.setPaymentValue(payment.getQuantity() * product.getCost());
        payments.put(payment.getProductCode(), payment);
    }

    public void removePaymentAndProduct(String productCode) {
        payments.remove(productCode);
        products.remove(productCode);
    }

    @Override
    public String toString() {
        int num = 0;
        StringBuilder sb = new StringBuilder(String.format("%n%-15s", "Order Id =")).append(invoiceId);
        sb.append(String.format("%n%-15s", "Order code:")).append(invoiceCode);
        sb.append(String.format("%n%-15s", "User name:")).append(userName);
        sb.append(String.format("%n%-15s", "Is payd:")).append(isPaid);
        sb.append(String.format("%n%-15s", "Order status:")).append(status);
        sb.append(String.format("%n%-15s", "Order date:")).append(date);
        sb.append(String.format("%n%-15s", "Order notes:")).append(invoiceNotes);
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

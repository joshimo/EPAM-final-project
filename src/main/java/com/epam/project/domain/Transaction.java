package com.epam.project.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/** Transaction entity mapped to transactions table */
public class Transaction implements Serializable {

    private Integer transactionId;
    private Integer paymentId;
    private Long invoiceCode;
    private String userName;
    private TransactionType transactionType;
    private Double paymentValue;
    private Timestamp time;
    private String notes;

    public Transaction() {
    }

    public Transaction(Integer paymentId, Long invoiceCode, String userName, Double paymentValue) {
        this.paymentId = paymentId;
        this.invoiceCode = invoiceCode;
        this.userName = userName;
        this.paymentValue = paymentValue;
    }

    /** Getters */
    public Integer getTransactionId() {
        return transactionId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Long getInvoiceCode() {
        return invoiceCode;
    }

    public String getUserName() {
        return userName;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Double getPaymentValue() {
        return paymentValue;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getNotes() {
        return notes;
    }


    /** Setters */
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public void setInvoiceCode(Long invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return  "\nTransaction Id: " + transactionId +
                "\nPayment Id: " + paymentId +
                "\nInvoice Code: " + invoiceCode +
                "\nUser Name: " + userName +
                "\nType: " + transactionType +
                "\nPayment Value: " + paymentValue +
                "\nTransaction time: " + time +
                "\nNotes: " + notes +
                "\n---------------------------------------------------------------------------------------------------";
    }
}

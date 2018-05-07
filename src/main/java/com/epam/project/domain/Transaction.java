package com.epam.project.domain;

/** Mapped to transactions table */
public class Transaction {

    private Integer transactionId;
    private Integer paymentId;
    private Long invoiceCode;
    private String userName;
    private Double paymentValue;
    private String notes;

    public Transaction() {
    }

    public Transaction(Integer paymentId, Long invoiceCode, String userName, Double paymentValue) {
        this.paymentId = paymentId;
        this.invoiceCode = invoiceCode;
        this.userName = userName;
        this.paymentValue = paymentValue;
    }

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

    public Double getPaymentValue() {
        return paymentValue;
    }

    public String getNotes() {
        return notes;
    }

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

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
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
                "\nPayment Value: " + paymentValue +
                "\nNotes: " + notes +
                "\n---------------------------------------------------------------------------------------------------";
    }
}

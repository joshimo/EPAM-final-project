package com.epam.project.domain;

import java.io.Serializable;

/** Payment entity mapped to payments table */
public class Payment implements Serializable {

    private Integer paymentId;
    private Long orderCode;
    private String productCode;
    private Double quantity;
    private Double paymentValue;
    private InvoiceStatus statusId;
    private String paymentNotes;

    public Payment() {
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Long getOrderCode() {
        return orderCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getPaymentValue() {
        return paymentValue;
    }

    public InvoiceStatus getStatusId() {
        return statusId;
    }

    public String getPaymentNotes() {
        return paymentNotes;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrderCode(Long orderCode) {
        this.orderCode = orderCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
    }

    public void setStatusId(InvoiceStatus statusId) {
        this.statusId = statusId;
    }

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes = paymentNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        if (paymentId != null ? !paymentId.equals(payment.paymentId) : payment.paymentId != null) return false;
        return orderCode.equals(payment.orderCode);
    }

    @Override
    public int hashCode() {
        int result = paymentId != null ? paymentId.hashCode() : 0;
        result = 31 * result + orderCode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("Payment Id = ").append(paymentId).append("\n");
        sb.append("Order Code: ").append(orderCode).append("; ");
        sb.append("Product Code: ").append(productCode).append("; ");
        sb.append("Quantity: ").append(quantity).append("; ");
        sb.append("Payment value: ").append(paymentValue).append("; ");
        sb.append("Status: ").append(statusId).append("; ");
        sb.append("Notes: ").append(paymentNotes).append(";\n");
        return sb.toString();
    }
}

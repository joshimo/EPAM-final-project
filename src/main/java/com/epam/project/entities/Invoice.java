package com.epam.project.entities;

import java.util.Map;

public class Invoice {
/*
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
    }*/
}

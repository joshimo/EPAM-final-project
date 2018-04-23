package entities;

import java.util.Locale;

/** Enity is mapped to project.stock table*/
public class Product {

    private Integer id;
    private Boolean isAvailable;
    private String name;
    private String description;
    private Double cost;
    private Double quantity;
    private String uom;
    private String notes;
    private Locale locale;

    public Product(Locale locale) {
    }

    public Integer getId() {
        return id;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getCost() {
        return cost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getUom() {
        return uom;
    }

    public String getNotes() {
        return notes;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
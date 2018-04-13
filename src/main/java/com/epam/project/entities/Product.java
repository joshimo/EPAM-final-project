package com.epam.project.entities;

import java.util.Locale;

/** 'Pruduct' Enity is mapped to project.stock table*/
public class Product {

    private Integer id;
    private Boolean isAvailable;
    private String nameEn;
    private String nameRu;
    private String descriptionEn;
    private String descriptionRu;
    private Double cost;
    private Double quantity;
    private String uomEn;
    private String uomRu;
    private String notesEn;
    private String notesRu;

    public Product() {
    }

    /** getters */
    public Integer getId() {
        return id;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public Double getCost() {
        return cost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getUomEn() {
        return uomEn;
    }

    public String getUomRu() {
        return uomRu;
    }

    public String getNotesEn() {
        return notesEn;
    }

    public String getNotesRu() {
        return notesRu;
    }

    /** Setters */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setUomEn(String uomEn) {
        this.uomEn = uomEn;
    }

    public void setUomRu(String uomRu) {
        this.uomRu = uomRu;
    }

    public void setNotesEn(String notesEn) {
        this.notesEn = notesEn;
    }

    public void setNotesRu(String notesRu) {
        this.notesRu = notesRu;
    }

    /** Add methods for Bilder template */
    public Product addAvailable(Boolean available) {
        isAvailable = available;
        return this;
    }

    public Product addNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public Product addNameRu(String nameRu) {
        this.nameRu = nameRu;
        return this;
    }

    public Product addDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public Product addDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
        return this;
    }

    public Product addCost(Double cost) {
        this.cost = cost;
        return this;
    }

    public Product addQuantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product addUomEn(String uomEn) {
        this.uomEn = uomEn;
        return this;
    }

    public Product addUomRu(String uomRu) {
        this.uomRu = uomRu;
        return this;
    }

    public Product addNotesEn(String notesEn) {
        this.notesEn = notesEn;
        return this;
    }

    public Product addNotesRu(String notesRu) {
        this.notesRu = notesRu;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return 31 * id.hashCode() + 31;
    }
}
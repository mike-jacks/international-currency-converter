package com.mikejacks.international_currency_converter.landedcost.entity;

import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(nullable = false, updatable = false)
    private UUID id;
    @Column()
    private String name;
    @Column()
    private Double price;
    @Column(length = 3)
    private String currencyCode;

    public Product() {}

    public Product(UUID id, String name, Double price, String currencyCode) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setCurrencyCode(currencyCode);
    }

    public Product(String name, Double price, String currencyCode) {
        this(UUID.randomUUID(), name, price, currencyCode);
    }

    /**
     * @return UUID return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Double return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        if  (currencyCode == null || !currencyCode.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.currencyCode = currencyCode.trim().toUpperCase();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}

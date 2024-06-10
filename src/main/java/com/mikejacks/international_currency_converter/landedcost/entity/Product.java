package com.mikejacks.international_currency_converter.landedcost.entity;

import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Represents a product entity in the system.
 *
 * <p>This class maps to a database table and contains information about a product,
 * including its unique identifier, name, price, and currency code.</p>
 */
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

    /**
     * Default constructor for creating a Product object.
     */
    public Product() {}

    /**
     * Constructs a new Product with the specified id, name, price, and currency code.
     *
     * @param id the unique identifier of the product.
     * @param name the name of the product.
     * @param price the price of the product.
     * @param currencyCode the currency code for the product's price.
     */
    public Product(UUID id, String name, Double price, String currencyCode) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setCurrencyCode(currencyCode);
    }

    /**
     * Constructs a new Product with a generated id, name, price, and currency code.
     *
     * @param name the name of the product.
     * @param price the price of the product.
     * @param currencyCode the currency code for the product's price.
     */
    public Product(String name, Double price, String currencyCode) {
        this(UUID.randomUUID(), name, price, currencyCode);
    }

    /**
     * Returns the unique identifier of the product.
     *
     * @return UUID representing the product's id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the product.
     *
     * @param id the UUID to set as the product's id.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the name of the product.
     *
     * @return String representing the product's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the name to set for the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the product.
     *
     * @return Double representing the product's price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price to set for the product.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns the currency code of the product's price.
     *
     * @return String representing the product's currency code.
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the currency code of the product's price.
     *
     * <p>The currency code must be exactly 3 characters long and only include letters.</p>
     *
     * @param currencyCode the currency code to set for the product.
     * @throws IllegalArgumentException if the currency code is invalid.
     */
    public void setCurrencyCode(String currencyCode) {
        if  (currencyCode == null || !currencyCode.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.currencyCode = currencyCode.trim().toUpperCase();
    }

    /**
     * Returns a string representation of the product.
     *
     * @return String representation of the product.
     */
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

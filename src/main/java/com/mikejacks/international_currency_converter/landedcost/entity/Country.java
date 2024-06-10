package com.mikejacks.international_currency_converter.landedcost.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Represents a country entity in the system.
 *
 * <p>This class maps to a database table and contains information about a country,
 * including its unique identifier, name, currency code, duty rate, and tax rate.</p>
 */
@Entity
public class Country {
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
    @Column(length = 3)
    private String code;
    @Column()
    private Double dutyRate;
    @Column()
    private Double taxRate;

    /**
     * Default constructor for creating a Country object.
     */
    public Country() {
        this.id = UUID.randomUUID();
    }

    /**
     * Constructs a new Country with the specified id, name, currency code, duty rate, and tax rate.
     *
     * @param id the unique identifier of the country.
     * @param name the name of the country.
     * @param code the currency code of the country.
     * @param dutyRate the duty rate of the country.
     * @param taxRate the tax rate of the country.
     */
    public Country(UUID id, String name, String code, Double dutyRate, Double taxRate) {
        this.setId(id);
        this.setName(name);
        this.setCode(code);
        this.setDutyRate(dutyRate);
        this.setTaxRate(taxRate);
    }

    /**
     * Constructs a new Country with a generated id, name, currency code, duty rate, and tax rate.
     *
     * @param name the name of the country.
     * @param code the currency code of the country.
     * @param dutyRate the duty rate of the country.
     * @param taxRate the tax rate of the country.
     */
    public Country(String name, String code, Double dutyRate, Double taxRate) {
        this(UUID.randomUUID(), name, code, dutyRate, taxRate);
    }

    /**
     * Returns the unique identifier of the country.
     *
     * @return UUID representing the country's id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the country.
     *
     * @param id the UUID to set as the country's id.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the name of the country.
     *
     * @return String representing the country's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the country.
     *
     * @param name the name to set for the country.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the duty rate of the country.
     *
     * @return Double representing the country's duty rate.
     */
    public Double getDutyRate() {
        return dutyRate;
    }

    /**
     * Sets the duty rate of the country.
     *
     * @param dutyRate the duty rate to set for the country.
     */
    public void setDutyRate(Double dutyRate) {
        this.dutyRate = dutyRate;
    }

    /**
     * Returns the tax rate of the country.
     *
     * @return Double representing the country's tax rate.
     */
    public Double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the tax rate of the country.
     *
     * @param taxRate the tax rate to set for the country.
     */
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Returns the currency code of the country.
     *
     * @return String representing the country's currency code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the currency code of the country.
     *
     * <p>The currency code must be exactly 3 characters long and only include letters.</p>
     *
     * @param code the currency code to set for the country.
     * @throws IllegalArgumentException if the currency code is invalid.
     */
    public void setCode(String code) {
        if  (code == null || !code.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.code = code.trim().toUpperCase();
    }

    /**
     * Returns a string representation of the country.
     *
     * @return String representation of the country.
     */
    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", dutyRate=" + dutyRate +
                ", taxRate=" + taxRate +
                '}';
    }
}

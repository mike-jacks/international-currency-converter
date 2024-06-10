package com.mikejacks.international_currency_converter.landedcost.model;

import javax.validation.constraints.NotNull;

/**
 * Represents the input data required to create a new country.
 *
 * <p>This class encapsulates the details necessary for creating a new country entity, including the country's name, code,
 * duty rate, and tax rate. It provides getter and setter methods for each field, along with validation for the country code.</p>
 */
public class CountryCreateInput {
    @NotNull
    private String name;

    @NotNull
    private String code;

    @NotNull
    private Double dutyRate;

    @NotNull
    private Double taxRate;

    /**
     * Default constructor for {@code CountryCreateInput}.
     */
    public CountryCreateInput() {}

    /**
     * Constructs a new {@code CountryCreateInput} with the specified details.
     *
     * @param name The name of the country. Must not be null.
     * @param code The code of the country. Must be exactly 3 characters long and only include letters. Must not be null.
     * @param dutyRate The duty rate of the country. Must not be null.
     * @param taxRate The tax rate of the country. Must not be null.
     */
    public CountryCreateInput(String name, String code, Double dutyRate, Double taxRate) {
       this.setName(name);
       this.setCode(code);
       this.setDutyRate(dutyRate);
       this.setTaxRate(taxRate);
    }

    /**
     * Retrieves the name of the country.
     *
     * @return The name of the country.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the country.
     *
     * @param name The name to set for the country.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the code of the country.
     *
     * @return The code of the country.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the country.
     *
     * <p>The code must be exactly 3 characters long and only include letters.
     * If the provided code does not meet these criteria, an {@code IllegalArgumentException} is thrown.</p>
     *
     * @param code The code to set for the country.
     * @throws IllegalArgumentException if the code is not exactly 3 characters long or includes non-letter characters.
     */
    public void setCode(String code) {
        if  (code == null || !code.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.code = code.trim().toUpperCase();
    }

    /**
     * Retrieves the duty rate of the country.
     *
     * @return The duty rate of the country.
     */
    public Double getDutyRate() {
        return dutyRate;
    }

    /**
     * Sets the duty rate of the country.
     *
     * @param dutyRate The duty rate to set for the country.
     */
    public void setDutyRate(Double dutyRate) {
        this.dutyRate = dutyRate;
    }

    /**
     * Retrieves the tax rate of the country.
     *
     * @return The tax rate of the country.
     */
    public Double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the tax rate of the country.
     *
     * @param taxRate The tax rate to set for the country.
     */
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Returns a string representation of the {@code CountryCreateInput} object.
     *
     * @return A string representation of the {@code CountryCreateInput} object.
     */
    @Override
    public String toString() {
        return "CountryCreateInput{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", dutyRate=" + dutyRate +
                ", taxRate=" + taxRate +
                '}';
    }
}

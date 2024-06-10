package com.mikejacks.international_currency_converter.landedcost.model;


import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import org.jetbrains.annotations.NotNull;

/**
 * A model class representing the input data required to update a {@link Country} entity.
 *
 * <p>The {@code CountryUpdateInput} class provides multiple constructors to create an instance
 * using different combinations of country attributes. It also includes validation for the country code
 * to ensure it is exactly three letters long.</p>
 */
public class CountryUpdateInput {
    private String name;
    private String code;
    private Double dutyRate;
    private Double taxRate;

    /**
     * Default constructor.
     */
    public CountryUpdateInput() {}

    /**
     * Constructs a new {@code CountryUpdateInput} with the specified attributes.
     *
     * @param name The name of the country.
     * @param code The code of the country.
     * @param dutyRate The duty rate of the country.
     * @param taxRate The tax rate of the country.
     */
    public CountryUpdateInput(String name, String code, Double dutyRate, Double taxRate) {
       this.setName(name);
       this.setCode(code);
       this.setDutyRate(dutyRate);
       this.setTaxRate(taxRate);
    }

    /**
     * Constructs a new {@code CountryUpdateInput} with the specified name, code, and duty rate.
     *
     * @param name The name of the country.
     * @param code The code of the country.
     * @param dutyRate The duty rate of the country.
     */
    public CountryUpdateInput(String name, String code, Double dutyRate) {
        this(name, code, dutyRate, null);
    }

    /**
     * Constructs a new {@code CountryUpdateInput} with the specified name and code.
     *
     * @param name The name of the country.
     * @param code The code of the country.
     */
    public CountryUpdateInput(String name, String code) {
        this(name, code, null);
    }

    /**
     * Constructs a new {@code CountryUpdateInput} with the specified name.
     *
     * @param name The name of the country.
     */
    public CountryUpdateInput(String name) {
        this(name, null);
    }

    /**
     * Constructs a new {@code CountryUpdateInput} using the attributes of the specified {@code Country}.
     *
     * @param country The {@code Country} object to copy attributes from.
     */
    public CountryUpdateInput(@NotNull Country country) {
        this.setName(country.getName());
        this.setCode(country.getCode());
        this.setDutyRate(country.getDutyRate());
        this.setTaxRate(country.getTaxRate());
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
     * Returns a string representation of the {@code CountryUpdateInput} object.
     *
     * @return A string representation of the {@code CountryUpdateInput} object.
     */
    @Override
    public String toString() {
        return "CountryUpdateInput{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", dutyRate=" + dutyRate +
                ", taxRate=" + taxRate +
                '}';
    }
}

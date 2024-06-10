package com.mikejacks.international_currency_converter.landedcost.model;

/**
 * Model class for updating product details.
 *
 * <p>This class represents the input data required to update a product's information, including its
 * name, price, and currency code. It provides getter and setter methods for each field, and includes
 * validation for the currency code format.</p>
 */
public class ProductUpdateInput {
    private String name;
    private Double price;
    private String currencyCode;

    /**
     * Default constructor.
     */
    public ProductUpdateInput() {}

    /**
     * Constructs a new {@code ProductUpdateInput} with the specified name, price, and currency code.
     *
     * @param name The name of the product.
     * @param price The price of the product.
     * @param currencyCode The currency code for the product's price.
     */
    public ProductUpdateInput(String name, Double price, String currencyCode) {
        this.setName(name);
        this.setPrice(price);
        this.setCurrencyCode(currencyCode);
    }

    /**
     * Gets the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The new name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the product.
     *
     * @return The price of the product.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price The new price of the product.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets the currency code for the product's price.
     *
     * @return The currency code for the product's price.
     */
    public String getCurrencyCode() { return currencyCode; }

    /**
     * Sets the currency code for the product's price.
     *
     * <p>The currency code must be exactly 3 characters long and consist only of letters. It is converted to
     * uppercase and trimmed of any leading or trailing whitespace.</p>
     *
     * @param currencyCode The new currency code for the product's price.
     * @throws IllegalArgumentException if the currency code is not exactly 3 letters.
     */
    public void setCurrencyCode(String currencyCode) {
        if  (currencyCode == null || !currencyCode.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.currencyCode = currencyCode.trim().toUpperCase();
    }

    /**
     * Returns a string representation of the {@code ProductUpdateInput} object.
     *
     * @return A string representation of the {@code ProductUpdateInput} object.
     */
    @Override
    public String toString() {
        return "ProductUpdateInput{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}

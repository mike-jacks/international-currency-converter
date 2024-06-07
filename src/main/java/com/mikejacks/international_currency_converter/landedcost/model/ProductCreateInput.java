package com.mikejacks.international_currency_converter.landedcost.model;

import javax.validation.constraints.NotNull;

public class ProductCreateInput {
    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private String currencyCode;

    public ProductCreateInput() {}

    public ProductCreateInput(String name, Double price, String currencyCode) {
        this.setName(name);
        this.setPrice(price);
        this.setCurrencyCode(currencyCode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

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
        return "ProductCreateInput{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
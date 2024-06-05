package com.mikejacks.international_currency_converter.landedcost.model;

public class ProductUpdateInput {
    private String name;
    private Double price;
    private String currencyCode;

    public ProductUpdateInput() {}

    public ProductUpdateInput(String name, Double price, String currencyCode) {
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

    public String getCurrencyCode() { return currencyCode; }

    public void setCurrencyCode(String currencyCode) {
        if  (currencyCode == null || !currencyCode.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.currencyCode = currencyCode.trim().toUpperCase();
    }
}

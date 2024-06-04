package com.mikejacks.international_currency_converter.landedcost.model;

public class ProductInput {
    private String name;
    private Double price;

    public ProductInput(String name, Double price) {
        this.setName(name);
        this.setPrice(price);

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
}

package com.mikejacks.international_currency_converter.landedcost.model;

public class LandedCost {
    private Double totalCost;

    public LandedCost() {

    }

    public LandedCost(Double totalCost) {
        this.setTotalCost(totalCost);
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}


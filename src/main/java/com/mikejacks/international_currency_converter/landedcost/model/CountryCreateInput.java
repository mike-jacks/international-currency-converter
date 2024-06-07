package com.mikejacks.international_currency_converter.landedcost.model;

import javax.validation.constraints.NotNull;

public class CountryCreateInput {
    @NotNull
    private String name;

    @NotNull
    private String code;

    @NotNull
    private Double dutyRate;

    @NotNull
    private Double taxRate;

    public CountryCreateInput() {}

    public CountryCreateInput(String name, String code, Double dutyRate, Double taxRate) {
       this.setName(name);
       this.setCode(code);
       this.setDutyRate(dutyRate);
       this.setTaxRate(taxRate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if  (code == null || !code.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.code = code.trim().toUpperCase();
    }

    public Double getDutyRate() {
        return dutyRate;
    }

    public void setDutyRate(Double dutyRate) {
        this.dutyRate = dutyRate;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

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

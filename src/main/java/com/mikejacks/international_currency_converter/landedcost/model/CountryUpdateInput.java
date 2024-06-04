package com.mikejacks.international_currency_converter.landedcost.model;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;

public class CountryUpdateInput {
    private String name;
    private String code;
    private Double dutyRate;
    private Double taxRate;

    public CountryUpdateInput() {}

    public CountryUpdateInput(String name, String code, Double dutyRate, Double taxRate) {
       this.setName(name);
       this.setCode(code);
       this.setDutyRate(dutyRate);
       this.setTaxRate(taxRate);
    }

    public CountryUpdateInput(String name, String code, Double dutyRate) {
        this(name, code, dutyRate, null);
    }

    public CountryUpdateInput(String name, String code) {
        this(name, code, null, null);
    }

    public CountryUpdateInput(String name) {
        this.setName(name);
        this.code = null;
        this.setDutyRate(null);
        this.setTaxRate(null);
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
}

package com.mikejacks.international_currency_converter.localization.model;

import org.jetbrains.annotations.NotNull;

public class CurrencyCreateInput {
    @NotNull
    private String baseCode;

    @NotNull
    private String targetCode;

    @NotNull
    private Double conversionRate;

    public CurrencyCreateInput() {}

    public CurrencyCreateInput(String baseCode, String targetCode, Double conversionRate) {
        this.setBaseCode(baseCode);
        this.setTargetCode(targetCode);
        this.setConversionRate(conversionRate);
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        if  (baseCode == null || !baseCode.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.baseCode = baseCode.trim().toUpperCase();
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        if  (targetCode == null || !targetCode.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.targetCode = targetCode.trim().toUpperCase();
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}

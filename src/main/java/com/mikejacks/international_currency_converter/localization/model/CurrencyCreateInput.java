package com.mikejacks.international_currency_converter.localization.model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the input data required to create a new currency.
 *
 * <p>This class encapsulates the details necessary to create a new currency, including the base code, target code,
 * and conversion rate. It provides validation to ensure that the currency codes are exactly 3 characters long and contain only letters.</p>
 */
public class CurrencyCreateInput {
    @NotNull
    private String baseCode;

    @NotNull
    private String targetCode;

    @NotNull
    private Double conversionRate;

    /**
     * Default constructor.
     */
    public CurrencyCreateInput() {
    }

    /**
     * Constructs a new {@code CurrencyCreateInput} with the specified base code, target code, and conversion rate.
     *
     * @param baseCode The base currency code. Must be exactly 3 letters.
     * @param targetCode The target currency code. Must be exactly 3 letters.
     * @param conversionRate The conversion rate between the base and target currencies.
     */
    public CurrencyCreateInput(String baseCode, String targetCode, Double conversionRate) {
        this.setBaseCode(baseCode);
        this.setTargetCode(targetCode);
        this.setConversionRate(conversionRate);
    }

    /**
     * Gets the base currency code.
     *
     * @return The base currency code.
     */
    public @NotNull String getBaseCode() {
        return baseCode;
    }

    /**
     * Sets the base currency code.
     *
     * @param baseCode The base currency code. Must be exactly 3 letters.
     * @throws IllegalArgumentException if the base code is null, not 3 characters long, or contains non-letter characters.
     */
    public void setBaseCode(@NotNull String baseCode) {
        if (!"[A-Za-z]{3}".matches(baseCode.trim())) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.baseCode = baseCode.trim().toUpperCase();
    }

    /**
     * Gets the target currency code.
     *
     * @return The target currency code.
     */
    public @NotNull String getTargetCode() {
        return targetCode;
    }

    /**
     * Sets the target currency code.
     *
     * @param targetCode The target currency code. Must be exactly 3 letters.
     * @throws IllegalArgumentException if the target code is null, not 3 characters long, or contains non-letter characters.
     */
    public void setTargetCode(@NotNull String targetCode) {
        if (!"[A-Za-z]{3}".matches(targetCode.trim())) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.targetCode = targetCode.trim().toUpperCase();
    }

    /**
     * Gets the conversion rate between the base and target currencies.
     *
     * @return The conversion rate.
     */
    public @NotNull Double getConversionRate() {
        return conversionRate;
    }

    /**
     * Sets the conversion rate between the base and target currencies.
     *
     * @param conversionRate The conversion rate.
     */
    public void setConversionRate(@NotNull Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}

package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;

import java.util.UUID;

/**
 * Service interface for calculating landed costs.
 *
 * <p>This interface defines the contract for calculating the landed cost of a product when shipped to a specific country,
 * considering various factors such as product price, duty rate, tax rate, and currency conversion rates.</p>
 */
public interface LandedCostService {

    /**
     * Calculates the landed cost of a product when shipped to a specific country.
     *
     * <p>This method calculates the total landed cost of a product by considering the product price, duty rate, tax rate,
     * and currency conversion rates. The calculation is based on the specified product ID, country ID, target currency code,
     * and base currency code.</p>
     *
     * @param productId The UUID of the product for which the landed cost is being calculated. Must not be null.
     * @param countryId The UUID of the country to which the product is being shipped. Must not be null.
     * @param targetCurrencyCode The currency code in which the landed cost is to be calculated. Must not be null.
     * @param baseCurrencyCode The base currency code used for conversion. Must not be null.
     * @return The {@code LandedCost} object containing the calculated total cost.
     */
    LandedCost calculateLandedCost(UUID productId, UUID countryId, String targetCurrencyCode, String baseCurrencyCode);
}

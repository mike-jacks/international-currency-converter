package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;
import com.mikejacks.international_currency_converter.landedcost.service.LandedCostService;
import com.mikejacks.international_currency_converter.localization.entity.Currency;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Mock implementation of the {@code LandedCostService} interface.
 *
 * <p>This service provides methods for calculating the landed cost of a product
 * when imported into a specified country, considering duty and tax rates, and
 * converting the cost to a target currency using predefined currency conversion rates.</p>
 */
@Service
public class MockLandedCostService implements LandedCostService {
    public List<Currency> currencies;

    public List<Product> products;

    public List<Country> countries;

    /**
     * Constructs a new {@code MockLandedCostService} with the given lists of currencies, products, and countries.
     *
     * @param currencies A list of {@code Currency} objects representing available currency conversion rates.
     * @param products A list of {@code Product} objects representing available products.
     * @param countries A list of {@code Country} objects representing available countries with duty and tax rates.
     */
    public MockLandedCostService(List<Currency> currencies, List<Product> products, List<Country> countries) {
        this.currencies = currencies;
        this.products = products;
        this.countries = countries;
    }


    /**
     * Calculates the landed cost of a product when imported into a specified country.
     *
     * <p>This method finds the specified product and country, retrieves the relevant currency conversion rates,
     * and calculates the total cost including duty and tax, converted to the target currency.</p>
     *
     * @param productId The UUID of the product to be imported.
     * @param countryId The UUID of the destination country.
     * @param targetCurrencyCode The currency code to which the total cost will be converted.
     * @param baseCurrencyCode The base currency code from which the conversion will be made.
     * @return A {@code LandedCost} object representing the total landed cost in the target currency.
     * @throws RuntimeException if the product, country, or currency conversion rates are not found.
     */
    @Override
    public LandedCost calculateLandedCost(UUID productId, UUID countryId, String targetCurrencyCode, String baseCurrencyCode) {
        Product foundProduct = products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Country foundCountry = countries.stream()
                .filter(country -> country.getId().equals(countryId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Country not found"));

        Currency foundCurrency = currencies.stream()
                .filter((currency) -> currency.getBaseCode().equals(baseCurrencyCode) && currency.getTargetCode().equals(targetCurrencyCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Currency not found"));

        // Calculate duty and tax
        Double duty = foundProduct.getPrice() * (foundCountry.getDutyRate() / 100);
        Double tax = foundProduct.getPrice() * (foundCountry.getTaxRate() / 100);
        Double totalCost = foundProduct.getPrice() + duty + tax;

        Double calculatedTotalCost = totalCost * foundCurrency.getConversionRate();

        return new LandedCost(calculatedTotalCost);
    }
}

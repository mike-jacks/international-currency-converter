package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;
import com.mikejacks.international_currency_converter.landedcost.service.LandedCostService;
import com.mikejacks.international_currency_converter.localization.entity.Currency;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MockLandedCostService implements LandedCostService {
    public List<Currency> currencies;

    public List<Product> products;

    public List<Country> countries;

    public MockLandedCostService(List<Currency> currencies, List<Product> products, List<Country> countries) {
        this.currencies = currencies;
        this.products = products;
        this.countries = countries;
    }


    /**
     * @param productId
     * @param countryId
     * @param targetCurrencyCode
     * @param baseCurrencyCode
     * @return
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

        Currency currencyConversionRateToBase = currencies.stream()
                .filter((currency) -> currency.getBaseCode().equals(targetCurrencyCode) && currency.getTargetCode().equals(baseCurrencyCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Currency not found"));

        Currency currencyConversionRateFromBase = currencies.stream()
                .filter((currency) -> currency.getBaseCode().equals(baseCurrencyCode) && currency.getTargetCode().equals(targetCurrencyCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Currency not found"));

        // Calculate duty and tax
        Double duty = foundProduct.getPrice() * (foundCountry.getDutyRate() / 100);
        Double tax = foundProduct.getPrice() * (foundCountry.getTaxRate() / 100);
        Double totalCost = foundProduct.getPrice() + duty + tax;

        // Convert total cost to base currency and then to target currency
        Double costInBaseCurrency = totalCost / currencyConversionRateToBase.getConversionRate();
        Double convertedTotalCost = costInBaseCurrency * currencyConversionRateFromBase.getConversionRate();

        return new LandedCost(convertedTotalCost);
    }
}

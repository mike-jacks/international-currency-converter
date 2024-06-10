package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;
import com.mikejacks.international_currency_converter.landedcost.service.LandedCostService;
import com.mikejacks.international_currency_converter.localization.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;

import com.mikejacks.international_currency_converter.landedcost.client.LocalizationGraphQLClient;
import com.mikejacks.international_currency_converter.landedcost.repository.CountryRepository;
import com.mikejacks.international_currency_converter.landedcost.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of the {@link LandedCostService} interface for calculating the landed cost of products.
 *
 * <p>This service calculates the landed cost of a product, which includes the product price, duty, and tax,
 * converted into a target currency.</p>
 *
 * <p>The service relies on database tables for accessing product and country data, and a GraphQL client
 * for fetching currency conversion rates.</p>
 *
 * @see LandedCostService
 * @see LandedCost
 * @see Product
 * @see Country
 * @see Currency
 * @see LocalizationGraphQLClient
 */
@Service
public class BaseLandedCostService implements LandedCostService {

    private ProductRepository productRepository;

    private CountryRepository countryRepository;

    private LocalizationGraphQLClient localizationGraphQLClient;

    /**
     * Constructs a new {@code BaseLandedCostService} with the specified repositories and GraphQL client.
     *
     * @param productRepository the repository for accessing product data
     * @param countryRepository the repository for accessing country data
     * @param localizationGraphQLClient the GraphQL client for fetching currency conversion rates
     */
    @Autowired
    public BaseLandedCostService(ProductRepository productRepository, CountryRepository countryRepository, LocalizationGraphQLClient localizationGraphQLClient) {
        this.productRepository = productRepository;
        this.countryRepository = countryRepository;
        this.localizationGraphQLClient = localizationGraphQLClient;

    }

    /**
     * Calculates the landed cost of a product in a specified country, converting the cost to a target currency.
     *
     * <p>This method retrieves the product and country details based on their UUIDs. It then fetches the currency
     * conversion rate from the base currency to the target currency using the localizationGraphQLClient. The method
     * calculates the duty and tax for the product based on the country's duty and tax rates. The total cost is then
     * computed by adding the product price, duty, and tax, and converting this total to the target currency using the
     * conversion rate.</p>
     *
     * @param productId The UUID of the product for which the landed cost is being calculated.
     * @param countryId The UUID of the country where the product is being imported.
     * @param targetCurrencyCode The currency code to which the cost should be converted.
     * @param baseCurrencyCode The base currency code of the product price.
     * @return A {@code LandedCost} object containing the calculated total cost in the target currency.
     * @throws RuntimeException if the product or country cannot be found.
     */
    @Override public LandedCost calculateLandedCost(UUID productId, UUID countryId, String targetCurrencyCode, String baseCurrencyCode) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Country country = countryRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country not found"));

        Currency currency = localizationGraphQLClient.getCurrency(baseCurrencyCode, targetCurrencyCode);

        Double duty = product.getPrice() * (country.getDutyRate() / 100);
        Double tax = product.getPrice() * (country.getTaxRate() / 100);
        Double totalCost = product.getPrice() + duty + tax;

        Double calculatedTotalCost = totalCost * currency.getConversionRate();

        return new LandedCost(calculatedTotalCost);
    }


}

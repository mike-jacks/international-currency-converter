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

@Service
public class BaseLandedCostService implements LandedCostService {

    private ProductRepository productRepository;

    private CountryRepository countryRepository;

    private LocalizationGraphQLClient localizationGraphQLClient;

    @Autowired
    public BaseLandedCostService(ProductRepository productRepository, CountryRepository countryRepository, LocalizationGraphQLClient localizationGraphQLClient) {
        this.productRepository = productRepository;
        this.countryRepository = countryRepository;
        this.localizationGraphQLClient = localizationGraphQLClient;

    }

    @Override public LandedCost calculateLandedCost(UUID productId, UUID countryId, String targetCurrencyCode, String baseCurrencyCode) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Country country = countryRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country not found"));

        Currency conversionRateToBase = localizationGraphQLClient.getCurrency(targetCurrencyCode, baseCurrencyCode);
        Currency conversionRateFromBase = localizationGraphQLClient.getCurrency(baseCurrencyCode, targetCurrencyCode);

        Double duty = product.getPrice() * (country.getDutyRate() / 100);
        Double tax = product.getPrice() * (country.getTaxRate() / 100);
        Double totalCost = product.getPrice() + duty + tax;

        Double costInBaseCurrency = totalCost / conversionRateToBase.getConversionRate();
        Double convertedTotalCost = costInBaseCurrency * conversionRateFromBase.getConversionRate();

        return new LandedCost(convertedTotalCost);
    }


}

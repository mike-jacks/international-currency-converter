package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;
import com.mikejacks.international_currency_converter.localization.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;

import com.mikejacks.international_currency_converter.landedcost.client.LocalizationGraphQLClient;
import com.mikejacks.international_currency_converter.landedcost.repository.CountryRepository;
import com.mikejacks.international_currency_converter.landedcost.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LandedCostService {

    private ProductRepository productRepository;

    private CountryRepository countryRepository;

    private LocalizationGraphQLClient localizationGraphQLClient;

    @Autowired
    public LandedCostService(ProductRepository productRepository, CountryRepository countryRepository, LocalizationGraphQLClient localizationGraphQLClient) {
        this.productRepository = productRepository;
        this.countryRepository = countryRepository;
        this.localizationGraphQLClient = localizationGraphQLClient;

    }

    public LandedCost calculateLandedCost(UUID productId, UUID countryId, String targetCurrencyCode, String baseCurrencyCode) {
        Product product = productRepository.findById(productId).orElseThrow();
        Country country = countryRepository.findById(countryId).orElseThrow();

        Currency conversionRateToBase = localizationGraphQLClient.getCurrency(baseCurrencyCode, targetCurrencyCode);
        Currency conversionRateFromBase = localizationGraphQLClient.getCurrency(baseCurrencyCode, targetCurrencyCode);

        Double duty = product.getPrice() * (country.getDutyRate() / 100);
        Double tax = product.getPrice() * (country.getTaxRate() / 100);
        Double totalCost = product.getPrice() + duty + tax;

        Double costInBaseCurrency = totalCost / conversionRateToBase.getConversionRate();
        Double convertedTotalCost = costInBaseCurrency * conversionRateFromBase.getConversionRate();

        LandedCost landedCost = new LandedCost(convertedTotalCost);
        return landedCost;

    }


}

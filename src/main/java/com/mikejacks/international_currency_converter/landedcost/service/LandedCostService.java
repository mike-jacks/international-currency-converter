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
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LocalizationGraphQLClient localizationGraphQLClient;

    public LandedCost calculateLandedCost(UUID productId, UUID countryId, String targetCurrencyCode, String baseCurrencyCode) {
        Product product = productRepository.findById(productId).orElseThrow();
        Country country = countryRepository.findById(countryId).orElseThrow();

        Currency conversionRateToBase = localizationGraphQLClient.getCurrency(baseCurrencyCode, targetCurrencyCode);
        Currency conversionRateFromBase = localizationGraphQLClient.getCurrency(baseCurrencyCode, targetCurrencyCode);

        Double duty = product.getPrice() * country.getDutyRate();
        Double tax = product.getPrice() * country.getTaxRate();
        Double totalCost = product.getPrice() + duty + tax;

        Double costInBaseCurrency = totalCost / conversionRateToBase.getConversionRate();
        Double convertedTotalCost = costInBaseCurrency * conversionRateFromBase.getConversionRate();

        LandedCost landedCost = new LandedCost();
        landedCost.setTotalCost(convertedTotalCost);
        return landedCost;

    }

    public List<Country> countries() {
        return countryRepository.findAll();
    }

    public Country countryById(UUID countryId) {
        return countryRepository.findById(countryId).orElse(null);
    }

    public Country countryByName(String name) {
        return countryRepository.findByName(name);
    }

    public Country countryByCode(String code) {
        return countryRepository.findByCode(code);
    }
}

package com.mikejacks.international_currency_converter.localization.service.impl;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.model.CurrencyCreateInput;
import com.mikejacks.international_currency_converter.localization.model.CurrencyUpdateInput;
import com.mikejacks.international_currency_converter.localization.service.CurrencyService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Mock implementation of the {@link CurrencyService} interface.
 *
 * <p>This service provides methods for managing currencies, including adding, updating,
 * retrieving, and updating conversion rates to live rates. It uses an in-memory list
 * to store currency data for simulation purposes.</p>
 *
 * <p>The class includes both query and mutation methods:</p>
 *
 * <ul>
 * <li>Query Methods:
 *   <ul>
 *     <li>{@link #currencies()}: Retrieves all currencies.</li>
 *     <li>{@link #currency(String, String)}: Retrieves a specific currency based on base code and target code.</li>
 *     <li>{@link #currencies(String, String)}: Retrieves currencies based on base code or target code.</li>
 *   </ul>
 * </li>
 * <li>Mutation Methods:
 *   <ul>
 *     <li>{@link #addCurrency(CurrencyCreateInput)}: Adds a new currency.</li>
 *     <li>{@link #updateCurrencyById(UUID, CurrencyUpdateInput)}: Updates an existing currency's details.</li>
 *     <li>{@link #updateCurrencyRateToLiveById(UUID)}: Updates the conversion rate of an existing currency to a live rate.</li>
 *   </ul>
 * </li>
 * </ul>
 *
 * <p>The in-memory list allows for the addition, modification, and retrieval of currency
 * objects for testing purposes without requiring a database.</p>
 *
 * @see CurrencyService
 * @see Currency
 * @see CurrencyCreateInput
 * @see CurrencyUpdateInput
 */
@Service
public class MockCurrencyService implements CurrencyService {
    public List<Currency> currencies;
    public ArrayList<Currency> mutableCurrencies;

    public MockCurrencyService(List<Currency> currencies) {
        this.currencies = currencies;
        this.mutableCurrencies = new ArrayList<>();
        for (Currency currency : currencies) {
            this.mutableCurrencies.add(new Currency(currency.getId(), currency.getBaseCode(), currency.getTargetCode(), currency.getConversionRate()));
        }
    }

    // Query Methods

    /**
     * Retrieves a list of all currencies.
     *
     * <p>This method fetches all {@code Currency} objects from the in-memory list and returns them as a list.</p>
     *
     * @return A list of all {@code Currency} objects.
     */
    @Override
    public List<Currency> currencies() {
        return currencies.stream().toList();
    }

    /**
     * Retrieves a currency based on the specified base code and target code.
     *
     * <p>This method searches through the list of currencies to find a {@code Currency} object that matches the provided base code
     * and target code. If a matching currency is found, it is returned. Otherwise, {@code null} is returned.</p>
     *
     * @param baseCode The base code of the currency to retrieve. Must not be null.
     * @param targetCode The target code of the currency to retrieve. Must not be null.
     * @return The {@code Currency} object matching the specified base code and target code, or {@code null} if no such currency is found.
     */
    @Override
    public Currency currency(@NotNull String baseCode, @NotNull  String targetCode) {
        return currencies.stream().filter(currency -> currency.getBaseCode().equals(baseCode) && currency.getTargetCode().equals(targetCode)).findFirst().orElse(null);
    }

    /**
     * Retrieves a list of currencies based on the specified base code or target code.
     *
     * <p>This method fetches currencies from the in-memory list based on the provided base code or target code.
     * If both base code and target code are provided, an {@code IllegalArgumentException} is thrown.
     * If only the base code is provided, it returns currencies with the specified base code.
     * If only the target code is provided, it returns currencies with the specified target code.
     * If neither base code nor target code is provided, it returns all currencies.</p>
     *
     * @param baseCode The base code of the currencies to retrieve. Can be null.
     * @param targetCode The target code of the currencies to retrieve. Can be null.
     * @return A list of {@code Currency} objects matching the specified criteria, or all currencies if no criteria are specified.
     * @throws IllegalArgumentException if both base code and target code are specified.
     */
    @Override
    public List<Currency> currencies(String baseCode, String targetCode) {
        if (baseCode != null && targetCode != null) {
            throw new IllegalArgumentException("Only one of either baseCode or targetCode can be specified. Not both.");
        } else if (baseCode != null) {
            return currencies.stream().filter(currency -> currency.getBaseCode().equals(baseCode)).toList();
        } else if (targetCode != null) {
            return currencies.stream().filter(currency -> currency.getTargetCode().equals(targetCode)).toList();
        } else {
            return currencies.stream().toList();
        }
    }

    // Mutation Methods

    /**
     * Adds a new currency to the list of mutable currencies.
     *
     * <p>This method creates a new {@code Currency} object using the details provided in the {@code CurrencyCreateInput} object.
     * It checks if a currency with the same base code and target code already exists in the list of mutable currencies.
     * If such a currency exists, an {@code IllegalArgumentException} is thrown. Otherwise, the new currency is added to the list
     * and returned.</p>
     *
     * @param currencyCreateInput A {@code CurrencyCreateInput} object containing the details of the new currency to be added.
     * @return The newly added {@code Currency} object.
     * @throws IllegalArgumentException if a currency with the same base code and target code already exists.
     */
    @Override
    public Currency addCurrency(@NotNull CurrencyCreateInput currencyCreateInput) {
        Currency newCurrency = new Currency(currencyCreateInput.getBaseCode(), currencyCreateInput.getTargetCode(), currencyCreateInput.getConversionRate());
        Currency existingCurrency = mutableCurrencies.stream().filter(currency-> currency.getBaseCode().equals(currencyCreateInput.getBaseCode()) && currency.getTargetCode().equals(currencyCreateInput.getTargetCode())).findFirst().orElse(null);
        if (existingCurrency != null) {
            throw new IllegalArgumentException("Currency wth base code: " + currencyCreateInput.getBaseCode() + ", and target code:" + currencyCreateInput.getTargetCode() + " already exists.");
        }
        mutableCurrencies.add(newCurrency);
        return newCurrency;
    }

    /**
     * Updates an existing currency's details by its ID.
     *
     * <p>This method finds an existing {@code Currency} object by its ID from the list of mutable currencies.
     * If the currency exists, it updates the base code, target code, and conversion rate if they are provided
     * in the {@code CurrencyUpdateInput} object. If the currency does not exist, an {@code IllegalArgumentException}
     * is thrown. The updated currency is then returned.</p>
     *
     * @param currencyId The ID of the currency to be updated.
     * @param currencyUpdateInput A {@code CurrencyUpdateInput} object containing the updated details of the currency.
     * @return The updated {@code Currency} object.
     * @throws IllegalArgumentException if a currency with the specified ID is not found.
     */
    @Override
    public Currency updateCurrencyById(UUID currencyId, CurrencyUpdateInput currencyUpdateInput) {
        Currency existingCurrency = mutableCurrencies.stream().filter(currency1 -> currency1.getId().equals(currencyId)).findFirst().orElse(null);
        if (existingCurrency == null) {
            throw new IllegalArgumentException("Currency with id " + currencyId + " not found.");
        }
        if (currencyUpdateInput.getBaseCode() != null) {
            existingCurrency.setBaseCode(currencyUpdateInput.getBaseCode());
        }
        if (currencyUpdateInput.getTargetCode() != null) {
            existingCurrency.setTargetCode(currencyUpdateInput.getTargetCode());
        }
        if (currencyUpdateInput.getConversionRate() != null) {
            existingCurrency.setConversionRate(currencyUpdateInput.getConversionRate());
        }
        return existingCurrency;

    }

    /**
     * Updates the conversion rate of the currency identified by the given currencyId to a live rate.
     *
     * <p>This method finds the existing currency by its ID from a list of mutable currencies. If the currency
     * is found, it updates its conversion rate by incrementing the current rate by 0.2. If the currency is not
     * found, it throws an IllegalArgumentException.
     *
     * @param currencyId The UUID of the currency to update.
     * @return The updated Currency object with the new conversion rate.
     * @throws IllegalArgumentException if the currency with the specified ID is not found.
     */
    @Override
    public Currency updateCurrencyRateToLiveById(UUID currencyId) {
        Currency existingCurrency = mutableCurrencies.stream().filter(currency -> currency.getId().equals(currencyId)).findFirst().orElse(null);
        if (existingCurrency == null) {
            throw new IllegalArgumentException("Currency with id " + currencyId + " not found.");
        }
        Double currentRate = existingCurrency.getConversionRate();
        Double newLiveRate = currentRate + 0.2;
        existingCurrency.setConversionRate(newLiveRate);
        return existingCurrency;
    }
}

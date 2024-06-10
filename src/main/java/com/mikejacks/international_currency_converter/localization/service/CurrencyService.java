package com.mikejacks.international_currency_converter.localization.service;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.model.CurrencyCreateInput;
import com.mikejacks.international_currency_converter.localization.model.CurrencyUpdateInput;

import java.util.List;
import java.util.UUID;

/**
 * Interface for Currency Service.
 *
 * <p>This interface defines the methods for managing currencies, including fetching currencies,
 * adding new currencies, and updating existing currencies.</p>
 */
public interface CurrencyService {

    /**
     * Retrieves a list of all currencies.
     *
     * @return A list of all {@code Currency} objects.
     */
    List<Currency> currencies();

    /**
     * Retrieves a currency based on the specified base code and target code.
     *
     * @param baseCode The base code of the currency to retrieve.
     * @param targetCode The target code of the currency to retrieve.
     * @return The {@code Currency} object matching the specified base code and target code,
     *         or {@code null} if no such currency is found.
     */
    Currency currency(String baseCode, String targetCode);

    /**
     * Retrieves a list of currencies based on the specified base code or target code.
     *
     * @param baseCode The base code of the currencies to retrieve. Can be null.
     * @param targetCode The target code of the currencies to retrieve. Can be null.
     * @return A list of {@code Currency} objects matching the specified criteria,
     *         or all currencies if no criteria are specified.
     */
    List<Currency> currenciesBy(String baseCode, String targetCode);

    /**
     * Adds a new currency.
     *
     * @param currency A {@code CurrencyCreateInput} object containing the details of the new currency to be added.
     * @return The newly added {@code Currency} object.
     */
    Currency addCurrency(CurrencyCreateInput currency);

    /**
     * Updates an existing currency's details by its ID.
     *
     * @param currencyId The ID of the currency to be updated.
     * @param currency A {@code CurrencyUpdateInput} object containing the updated details of the currency.
     * @return The updated {@code Currency} object.
     */
    Currency updateCurrencyById(UUID currencyId, CurrencyUpdateInput currency);

    /**
     * Updates the conversion rate of the currency identified by the given currencyId to a live rate.
     *
     * @param currencyId The UUID of the currency to update.
     * @return The updated {@code Currency} object with the new conversion rate.
     */
    Currency updateCurrencyRateToLiveById(UUID currencyId);
}

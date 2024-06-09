package com.mikejacks.international_currency_converter.localization.datafetcher.query;

import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.service.CurrencyService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;

/**
 * Data fetcher for currency queries in the GraphQL API.
 *
 * <p>This class handles the GraphQL queries related to currencies, utilizing the {@code CurrencyService}
 * to fetch currency data based on different criteria.</p>
 */
@DgsComponent
public class CurrencyQueryDataFetcher {
    private final CurrencyService currencyService;

    /**
     * Constructs a new {@code CurrencyQueryDataFetcher} with the specified {@code CurrencyService}.
     *
     * @param currencyService The service used to fetch currency data.
     */
    @Autowired
    public CurrencyQueryDataFetcher(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    /**
     * Fetches a list of all available currencies.
     *
     * <p>This method queries the CurrencyService to retrieve a list of all currency entities.
     * It does not require any input arguments and returns a list of Currency objects.</p>
     *
     * @return a list of Currency objects representing all available currencies
     */
    @DgsQuery
    public List<Currency> currencies() {
        return currencyService.currencies();
    }

    /**
     * Retrieves a currency based on the specified base code and target code.
     *
     * @param baseCode The base currency code.
     * @param targetCode The target currency code.
     * @return The {@code Currency} object matching the specified base code and target code.
     */
    @DgsQuery
    public Currency currency(@InputArgument final String baseCode, @InputArgument final String targetCode) {
        return currencyService.currency(baseCode, targetCode);
    }

    /**
     * Retrieves a list of currencies based on the specified base code or target code.
     *
     * @param baseCode The base currency code. Can be null.
     * @param targetCode The target currency code. Can be null.
     * @return A list of {@code Currency} objects matching the specified criteria.
     */

    @DgsQuery
    public List<Currency> currenciesBy(@InputArgument final String baseCode, @InputArgument final String targetCode) {
        return currencyService.currencies(baseCode, targetCode);
    }

    /**
     * Retrieves a list of currencies based on the specified base code.
     *
     * @param baseCode The base currency code.
     * @return A list of {@code Currency} objects with the specified base code.
     */
    @DgsQuery
    public List<Currency> currenciesByBaseCode(@InputArgument final String baseCode) {
        return currencyService.currencies(baseCode, null);
    }

    /**
     * Retrieves a list of currencies based on the specified target code.
     *
     * @param targetCode The target currency code.
     * @return A list of {@code Currency} objects with the specified target code.
     */
    @DgsQuery
    public List<Currency> currenciesByTargetCode(@InputArgument final String targetCode) {
        return currencyService.currencies(null, targetCode);
    }


}

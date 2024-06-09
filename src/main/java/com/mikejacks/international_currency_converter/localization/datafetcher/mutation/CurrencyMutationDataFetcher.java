package com.mikejacks.international_currency_converter.localization.datafetcher.mutation;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.model.CurrencyCreateInput;
import com.mikejacks.international_currency_converter.localization.model.CurrencyUpdateInput;
import com.mikejacks.international_currency_converter.localization.service.CurrencyService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Data fetcher for currency mutations in the GraphQL API.
 *
 * <p>This class handles the GraphQL mutations related to currencies, utilizing the {@code CurrencyService}
 * to add and update currency data.</p>
 */
@DgsComponent
public class CurrencyMutationDataFetcher {
    private final CurrencyService currencyService;

    /**
     * Constructs a new {@code CurrencyMutationDataFetcher} with the specified {@code CurrencyService}.
     *
     * @param currencyService The service used to handle currency data operations.
     */
    @Autowired
    public CurrencyMutationDataFetcher(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Adds a new currency based on the provided input data.
     *
     * @param currency The {@code CurrencyCreateInput} object containing the details of the currency to add.
     * @return The newly created {@code Currency} object.
     */
    @DgsMutation
    public Currency addCurrency(@InputArgument CurrencyCreateInput currency) {
        return currencyService.addCurrency(currency);
    }

    /**
     * Updates an existing currency identified by the specified ID with the provided input data.
     *
     * @param currencyId The unique ID of the currency to update.
     * @param currency The {@code CurrencyUpdateInput} object containing the updated details of the currency.
     * @return The updated {@code Currency} object.
     */
    @DgsMutation
    public Currency updateCurrencyById(@InputArgument UUID currencyId, @InputArgument CurrencyUpdateInput currency) {
        return currencyService.updateCurrencyById(currencyId, currency);
    }
}

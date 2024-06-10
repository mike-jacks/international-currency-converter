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
     * <p>This mutation method allows the addition of a new currency by accepting a {@code CurrencyCreateInput} object
     * containing the details of the currency to be added. It delegates the creation operation to the {@code currencyService}.</p>
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
     * <p>This mutation method allows updating the details of an existing currency by accepting the currency's unique ID
     * and a {@code CurrencyUpdateInput} object containing the updated details. The update operation is delegated to the
     * {@code currencyService}.</p>
     *
     * @param currencyId The unique ID of the currency to update.
     * @param currency The {@code CurrencyUpdateInput} object containing the updated details of the currency.
     * @return The updated {@code Currency} object.
     */
    @DgsMutation
    public Currency updateCurrencyById(@InputArgument UUID currencyId, @InputArgument CurrencyUpdateInput currency) {
        return currencyService.updateCurrencyById(currencyId, currency);
    }

    /**
     * Updates the conversion rate of a currency to its live rate based on the specified currency ID.
     *
     * <p>This mutation method updates the conversion rate of an existing {@code Currency} object to its live rate.
     * It uses the provided {@code currencyId} to identify the currency to be updated and delegates the update
     * operation to the {@code currencyService}.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * mutation {
     *     updateCurrencyRateToLiveById(currencyId: "123e4567-e89b-12d3-a456-426614174000") {
     *         id
     *         baseCode
     *         targetCode
     *         conversionRate
     *     }
     * }
     * }</pre>
     *
     * @param currencyId The UUID of the currency to update. Must not be null.
     * @return The updated {@code Currency} object with the new live conversion rate.
     * @throws IllegalArgumentException if the currency with the specified ID does not exist.
     * @throws RuntimeException if the update operation fails due to an error in fetching the live rate.
     */
    @DgsMutation
    public Currency updateCurrencyRateToLiveById(@InputArgument UUID currencyId) {
        return currencyService.updateCurrencyRateToLiveById(currencyId);
    }
}

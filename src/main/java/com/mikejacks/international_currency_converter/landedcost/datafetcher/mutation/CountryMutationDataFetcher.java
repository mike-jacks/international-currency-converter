package com.mikejacks.international_currency_converter.landedcost.datafetcher.mutation;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.service.CountryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Data fetcher for country mutations in the GraphQL API.
 *
 * <p>This class handles the GraphQL mutations related to countries, utilizing the {@code CountryService}
 * to add, update, and delete country data.</p>
 */
@DgsComponent
public class CountryMutationDataFetcher {
    private final CountryService countryService;

    /**
     * Constructs a new {@code CountryMutationDataFetcher} with the specified {@code CountryService}.
     *
     * @param countryService The service used to handle country data operations.
     */
    @Autowired
    public CountryMutationDataFetcher(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Adds a new country based on the provided input data.
     *
     * @param country The {@code CountryCreateInput} object containing the details of the country to add.
     * @return The newly created {@code Country} object.
     */
    @DgsMutation
    public Country addCountry(@InputArgument CountryCreateInput country) {
        return countryService.addCountry(country);
    }

    /**
     * Updates an existing country identified by the specified ID or name with the provided input data.
     *
     * @param countryId The unique ID of the country to update. Can be null.
     * @param name The name of the country to update. Can be null.
     * @param country The {@code CountryUpdateInput} object containing the updated details of the country.
     * @return The updated {@code Country} object.
     * @throws IllegalArgumentException if both countryId and name are provided, or if neither are provided.
     */
    @DgsMutation
    public Country updateCountry(@InputArgument UUID countryId, @InputArgument String name, @InputArgument CountryUpdateInput country) {
        return countryService.updateCountry(countryId, name, country);
    }

    /**
     * Updates an existing country identified by the specified ID with the provided input data.
     *
     * @param countryId The unique ID of the country to update. Must not be null.
     * @param country The {@code CountryUpdateInput} object containing the updated details of the country.
     * @return The updated {@code Country} object.
     * @throws IllegalArgumentException if countryId is null.
     */
    @DgsMutation
    public Country updateCountryById(@InputArgument UUID countryId, @InputArgument CountryUpdateInput country) {
        return countryService.updateCountry(countryId, null, country);
    }

    /**
     * Updates an existing country identified by the specified name with the provided input data.
     *
     * @param name The name of the country to update. Must not be null.
     * @param country The {@code CountryUpdateInput} object containing the updated details of the country.
     * @return The updated {@code Country} object.
     * @throws IllegalArgumentException if name is null.
     */
    @DgsMutation
    public Country updateCountryByName(@InputArgument String name, @InputArgument CountryUpdateInput country) {
        return countryService.updateCountry(null, name, country);
    }

    /**
     * Deletes an existing country identified by the specified ID.
     *
     * @param countryId The unique ID of the country to delete. Must not be null.
     * @return A {@code DeleteItemResponse} indicating the success or failure of the deletion operation.
     * @throws IllegalArgumentException if countryId is null.
     */
    @DgsMutation
    public DeleteItemResponse deleteCountryById(@InputArgument UUID countryId) {
        return countryService.deleteCountryById(countryId);
    }
}

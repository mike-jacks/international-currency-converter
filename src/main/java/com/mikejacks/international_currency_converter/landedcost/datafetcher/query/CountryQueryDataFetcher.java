package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.service.CountryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * Data fetcher for country queries in the GraphQL API.
 *
 * <p>This class handles the GraphQL queries related to countries, utilizing the {@code CountryService}
 * to fetch country data.</p>
 */
@DgsComponent
public class CountryQueryDataFetcher {
    private final CountryService countryService;

    /**
     * Constructs a new {@code CountryQueryDataFetcher} with the specified {@code CountryService}.
     *
     * @param countryService The service used to handle country data operations.
     */
    @Autowired
    public CountryQueryDataFetcher(final CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Retrieves a list of all countries.
     *
     * <p>This method fetches all {@code Country} objects from the service and returns them as a list.</p>
     *
     * @return A list of all {@code Country} objects.
     */
    @DgsQuery
    public List<Country> countries() {
        return countryService.countries();
    }

    /**
     * Retrieves a country based on the specified country ID, name, or code.
     *
     * <p>This method fetches a {@code Country} object from the service based on the provided country ID, name, or code.
     * Only one of the arguments should be provided; otherwise, an exception is thrown.</p>
     *
     * @param countryId The unique ID of the country. Can be null.
     * @param name The name of the country. Can be null.
     * @param code The code of the country. Can be null.
     * @return The {@code Country} object matching the specified criteria, or {@code null} if no such country is found.
     * @throws IllegalArgumentException if no arguments or multiple arguments are provided.
     */
    @DgsQuery
    public Country country(@InputArgument final UUID countryId, @InputArgument final String name, @InputArgument final String code) {
        return countryService.country(countryId, name, code);
    }

    /**
     * Retrieves a country based on the specified country ID.
     *
     * <p>This method fetches a {@code Country} object from the service based on the provided country ID.</p>
     *
     * @param countryId The unique ID of the country. Must not be null.
     * @return The {@code Country} object matching the specified ID, or {@code null} if no such country is found.
     */
    @DgsQuery
    public Country countryById(@InputArgument final UUID countryId) {
        return countryService.country(countryId, null, null);
    }

    /**
     * Retrieves a country based on the specified country name.
     *
     * <p>This method fetches a {@code Country} object from the service based on the provided country name.</p>
     *
     * @param name The name of the country. Must not be null.
     * @return The {@code Country} object matching the specified name, or {@code null} if no such country is found.
     */
    @DgsQuery
    public Country countryByName(@InputArgument final String name) {
        return countryService.country(null, name, null);
    }

    /**
     * Retrieves a country based on the specified country code.
     *
     * <p>This method fetches a {@code Country} object from the service based on the provided country code.</p>
     *
     * @param code The code of the country. Must not be null.
     * @return The {@code Country} object matching the specified code, or {@code null} if no such country is found.
     */
    @DgsQuery
    public Country countryByCode(@InputArgument final String code) {
        return countryService.country(null, null, code);
    }

}
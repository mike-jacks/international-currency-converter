package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing countries.
 *
 * <p>This interface defines the contract for operations related to countries, including querying, adding, updating,
 * and deleting countries.</p>
 */
public interface CountryService {
    // Query Services

    /**
     * Retrieves a list of all countries.
     *
     * <p>This method fetches all {@code Country} objects and returns them as a list.</p>
     *
     * @return A list of all {@code Country} objects.
     */
    List<Country> countries();

    /**
     * Retrieves a country based on the specified criteria.
     *
     * <p>This method fetches a {@code Country} object based on the provided country ID, name, or code. If more than one
     * criterion is provided or no criteria are provided, an {@code IllegalArgumentException} is thrown.</p>
     *
     * @param countryId The UUID of the country to retrieve. Can be null.
     * @param name The name of the country to retrieve. Can be null.
     * @param code The code of the country to retrieve. Can be null.
     * @return The {@code Country} object matching the specified criteria, or {@code null} if no such country is found.
     * @throws IllegalArgumentException if more than one or no criteria are provided.
     */
    Country country(UUID countryId, String name, String code);

    // Mutation Services

    /**
     * Adds a new country to the database.
     *
     * <p>This method creates a new {@code Country} object using the details provided in the {@code CountryCreateInput} object.
     * The new country is then saved to the database.</p>
     *
     * @param countryCreateInput A {@code CountryCreateInput} object containing the details of the new country to be added.
     * @return The newly added {@code Country} object.
     */
    Country addCountry(@NotNull CountryCreateInput countryCreateInput);

    /**
     * Updates an existing country's details by its ID or name.
     *
     * <p>This method finds an existing {@code Country} object by its ID or name. If the country exists, it updates its details
     * with the information provided in the {@code CountryUpdateInput} object. If the country does not exist, an {@code IllegalArgumentException}
     * is thrown. The updated country is then saved to the database and returned.</p>
     *
     * @param countryId The UUID of the country to be updated. Can be null if name is provided.
     * @param name The name of the country to be updated. Can be null if countryId is provided.
     * @param country A {@code CountryUpdateInput} object containing the updated details of the country.
     * @return The updated {@code Country} object.
     * @throws IllegalArgumentException if the country does not exist or if both countryId and name are provided.
     */
    Country updateCountry(UUID countryId, String name, @NotNull CountryUpdateInput country);

    /**
     * Deletes an existing country by its ID.
     *
     * <p>This method deletes a {@code Country} object identified by the given country ID. If the country does not exist,
     * a {@code DeleteItemResponse} indicating failure is returned. Otherwise, a {@code DeleteItemResponse} indicating
     * success is returned.</p>
     *
     * @param countryId The UUID of the country to be deleted.
     * @return A {@code DeleteItemResponse} indicating the result of the delete operation.
     */
    DeleteItemResponse deleteCountryById(@NotNull UUID countryId);
}

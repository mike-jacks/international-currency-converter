package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.repository.CountryRepository;
import com.mikejacks.international_currency_converter.landedcost.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

/**
 * Service implementation for handling operations related to countries.
 */
@Service
public class BaseCountryService implements CountryService {

    private final CountryRepository countryRepository;

    /**
     * Constructs a new {@code BaseCountryService} with the specified {@code CountryRepository}.
     *
     * @param countryRepository The repository used to handle country data operations.
     */
    @Autowired
    public BaseCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // Query Services

    /**
     * Retrieves a list of all countries.
     *
     * @return A list of all {@code Country} objects.
     */
    @Override public List<Country> countries() {
        return countryRepository.findAll();
    }

    /**
     * Retrieves a country based on the specified ID, name, or code.
     *
     * @param countryId The UUID of the country to retrieve. Can be null.
     * @param name The name of the country to retrieve. Can be null.
     * @param code The code of the country to retrieve. Can be null.
     * @return The {@code Country} object matching the specified criteria, or {@code null} if no such country is found.
     * @throws IllegalArgumentException if both or none of the arguments are provided.
     */
    @Override public Country country(final UUID countryId, final String name, final String code) {
        if (countryId != null && name == null && code == null) {
            return countryRepository.findCountryById(countryId).orElse(null);
        }
        if (countryId == null && name != null && code == null) {
            return countryRepository.findCountryByName(name).orElse(null);
        }
        if (countryId == null && name == null && code != null) {
            return countryRepository.findCountryByCode(code).orElse(null);
        }
        if (countryId == null && name == null && code == null) {
            throw new IllegalArgumentException("No arguments present. You must have only one of the following arguments: countryId, name, or code");
        } else {
            throw new IllegalArgumentException("Too many arguments present. You must have only one of the following arguments: countryId, name, or code");
        }
    }

    // Mutation Services

    /**
     * Adds a new country to the database.
     *
     * @param countryCreateInput A {@code CountryCreateInput} object containing the details of the new country to be added.
     * @return The newly added {@code Country} object.
     */
    @Override public Country addCountry(@NotNull final CountryCreateInput countryCreateInput) {
        System.out.println("Adding country: " + countryCreateInput);
        Country newCountry = new Country(
                countryCreateInput.getName(),
                countryCreateInput.getCode(),
                countryCreateInput.getDutyRate(),
                countryCreateInput.getTaxRate()
        );
        Country savedCountry = countryRepository.save(newCountry);
        System.out.println("Country saved: " + savedCountry);
        return savedCountry;
    }

    /**
     * Updates an existing country based on the provided ID or name with the provided input data.
     *
     * @param countryId The UUID of the country to update. Can be null.
     * @param name The name of the country to update. Can be null.
     * @param country The {@code CountryUpdateInput} object containing the updated details of the country.
     * @return The updated {@code Country} object.
     * @throws IllegalArgumentException if both or none of the arguments are provided, or if the country is not found.
     */
    @Override public Country updateCountry(final UUID countryId, final String name, final @NotNull CountryUpdateInput country) {
        Country existingCountry;
        if (countryId != null && name != null) {
           throw new IllegalArgumentException("only a countryId or a name must be provided. Not both.");
        }else if (countryId != null) {
            existingCountry = countryRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country with id " + countryId + " not found."));
        } else if (name != null) {
            existingCountry = countryRepository.findCountryByName(name).orElseThrow(() -> new RuntimeException("Country with name " + name + " not found."));
        } else throw new IllegalArgumentException("either a countryId or a name must be provided");

        if (country.getName() != null) {
            existingCountry.setName(country.getName());
        }
        if (country.getCode() != null) {
            existingCountry.setCode(country.getCode());
        }
        if (country.getDutyRate() != null) {
            existingCountry.setDutyRate(country.getDutyRate());
        }
        if (country.getTaxRate() != null) {
            existingCountry.setTaxRate(country.getTaxRate());
        }
        return countryRepository.save(existingCountry);
    }

    /**
     * Deletes a country by its ID.
     *
     * @param countryId The UUID of the country to be deleted. Must not be null.
     * @return A {@code DeleteItemResponse} indicating the success or failure of the deletion operation.
     *         If the country is successfully deleted, the response contains the country ID and a success message.
     *         If the country is not found, the response contains a failure message and a null country ID.
     */
    @Override public DeleteItemResponse deleteCountryById(final @NotNull UUID countryId) {
       Optional<Country> existingCountryOptional = countryRepository.findById(countryId);
       if (existingCountryOptional.isEmpty()) {
           return new DeleteItemResponse(false, String.format("unable to find country with the id %s", countryId), null);
       }
       Country existingCountry = existingCountryOptional.get();
       countryRepository.delete(existingCountry);
       return new DeleteItemResponse(true, String.format("'%s' has been successfully deleted.", existingCountry.getName()), countryId);
    }
}

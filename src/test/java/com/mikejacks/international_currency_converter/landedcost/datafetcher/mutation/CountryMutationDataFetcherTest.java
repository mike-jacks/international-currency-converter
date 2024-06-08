package com.mikejacks.international_currency_converter.landedcost.datafetcher.mutation;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.service.impl.MockCountryService;
import com.netflix.graphql.dgs.DgsComponent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DgsComponent
class CountryMutationDataFetcherTest {

    private static @NotNull Stream<Arguments> mockCountriesList() {
        return Stream.of(
                Arguments.of(Collections.emptyList(), "Empty List"),
                Arguments.of(Collections.singletonList(new Country("Tatooine", "TTO", 4.0, 5.0)), "One item"),
                Arguments.of(Arrays.asList(
                        new Country("Coruscant", "CSD", 5.0, 2.0),
                        new Country("Naboo", "NAB", 4.0, 1.0),
                        new Country("Hoth", "HOT", 3.0, 10.0),
                        new Country("Endor", "END", 4.0, 5.0),
                        new Country("Tatooine", "TTO", 4.0, 5.0)
                ), "Many items")
        );
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCountriesList")
    void testAddCountry(@NotNull List<Country> countries, String testName) {
        Integer countriesBeginningListSize = countries.size();
        MockCountryService mockCountryService = new MockCountryService(countries);
        CountryMutationDataFetcher countryMutationDataFetcher = new CountryMutationDataFetcher(mockCountryService);
        CountryCreateInput newCountry = new CountryCreateInput("Alderon", "ALD", 3.3, 5.2);
        Country addedCountry = countryMutationDataFetcher.addCountry(newCountry);
        assertEquals(newCountry.getName(), addedCountry.getName());
        assertEquals(newCountry.getCode(), addedCountry.getCode());
        assertEquals(newCountry.getDutyRate(), addedCountry.getDutyRate());
        assertEquals(newCountry.getTaxRate(), addedCountry.getTaxRate());
        assertEquals(countriesBeginningListSize+1, mockCountryService.mutableCountries.size());
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCountriesList")
    void testUpdateCountry(@NotNull List<Country> countries, String testName) {
        Integer countriesBeginningListSize = countries.size();
        MockCountryService mockCountryService = new MockCountryService(countries);
        CountryMutationDataFetcher countryMutationDataFetcher = new CountryMutationDataFetcher(mockCountryService);
        CountryUpdateInput newCountry = new CountryUpdateInput("Alderon", "ALD", 3.3, 5.2);
        if (countriesBeginningListSize == 0) {
            UUID randomId = UUID.randomUUID();
            String randomName = "YavinIV";

            IllegalArgumentException updateCountryByIdException = assertThrows(IllegalArgumentException.class, () -> countryMutationDataFetcher.updateCountry(randomId, null, newCountry));
            assertEquals("Country with id " + randomId + " not found.", updateCountryByIdException.getMessage());

            IllegalArgumentException updateCountryByNameException = assertThrows(IllegalArgumentException.class, () -> countryMutationDataFetcher.updateCountry(null, randomName, newCountry));
            assertEquals("Country with name " + randomName + " not found.", updateCountryByNameException.getMessage());

            IllegalArgumentException updateCountryByNullArgs = assertThrows(IllegalArgumentException.class, () -> countryMutationDataFetcher.updateCountry(null, null, newCountry));
            assertEquals("either a countryId or name must be provided", updateCountryByNullArgs.getMessage());

            IllegalArgumentException updateCountryByBothArgs = assertThrows(IllegalArgumentException.class, () -> countryMutationDataFetcher.updateCountry(randomId, randomName, newCountry));
            assertEquals("only a countryId or a name must be provided. Not both.", updateCountryByBothArgs.getMessage());

        } else {
            Country existingCountry = countries.get(countriesBeginningListSize -1);
            UUID existingCountryId = existingCountry.getId();
            String existingCountryName = existingCountry.getName();
            String existingCountryCode = existingCountry.getCode();
            Double existingCountryDutyRate = existingCountry.getDutyRate();
            Double existingCountryTaxRate = existingCountry.getTaxRate();

            Country updatedCountryById = countryMutationDataFetcher.updateCountry(existingCountryId, null, newCountry);
            assertEquals(newCountry.getName(), updatedCountryById.getName());

            Country resetCountryById = countryMutationDataFetcher.updateCountry(existingCountryId, null, new CountryUpdateInput(existingCountryName, existingCountryCode, existingCountryDutyRate, existingCountryTaxRate));
            assertEquals(existingCountry.getName(), resetCountryById.getName());

            Country updatedCountryByName = countryMutationDataFetcher.updateCountry(null, existingCountryName, newCountry);
            assertEquals(newCountry.getName(), updatedCountryByName.getName());
            assertEquals(countriesBeginningListSize, mockCountryService.mutableCountries.size());
        }
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCountriesList")
    void testUpdateCountryById(@NotNull List<Country> countries, String testName) {
        Integer countriesBeginningListSize = countries.size();
        MockCountryService mockCountryService = new MockCountryService(countries);
        CountryMutationDataFetcher countryMutationDataFetcher = new CountryMutationDataFetcher(mockCountryService);
        CountryUpdateInput newCountry = new CountryUpdateInput("Alderon", "ALD", 3.3, 5.2);
        if (countriesBeginningListSize == 0) {
            UUID randomUUID = UUID.randomUUID();
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> countryMutationDataFetcher.updateCountryById(randomUUID, newCountry));
            assertEquals("Country with id " + randomUUID + " not found.", exception.getMessage());
        } else {
            UUID existingCountryId = countries.get(countriesBeginningListSize - 1).getId();
            Country updatedCountry = countryMutationDataFetcher.updateCountryById(existingCountryId, newCountry);
            assertEquals(newCountry.getName(), updatedCountry.getName());
            assertEquals(newCountry.getCode(), updatedCountry.getCode());
            assertEquals(newCountry.getDutyRate(), updatedCountry.getDutyRate());
            assertEquals(newCountry.getTaxRate(), updatedCountry.getTaxRate());
            assertEquals(countriesBeginningListSize, mockCountryService.mutableCountries.size());
        }
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCountriesList")
    void testUpdateCountryByName(@NotNull List<Country> countries, String testName) {
        Integer countriesBeginningListSize = countries.size();
        MockCountryService mockCountryService = new MockCountryService(countries);
        CountryMutationDataFetcher countryMutationDataFetcher = new CountryMutationDataFetcher(mockCountryService);
        CountryUpdateInput newCountry = new CountryUpdateInput("Alderon", "ALD", 3.3, 5.2);
        if (countriesBeginningListSize == 0) {
            String randomName = "Dagobah";
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> countryMutationDataFetcher.updateCountryByName(randomName, newCountry));
            assertEquals("Country with name " + randomName + " not found.", exception.getMessage());
        } else {
            String existingCountryName = countries.get(countriesBeginningListSize - 1).getName();
            Country updatedCountry = countryMutationDataFetcher.updateCountryByName(existingCountryName, newCountry);
            assertEquals(newCountry.getName(), updatedCountry.getName());
            assertEquals(newCountry.getCode(), updatedCountry.getCode());
            assertEquals(newCountry.getDutyRate(), updatedCountry.getDutyRate());
            assertEquals(newCountry.getTaxRate(), updatedCountry.getTaxRate());
            assertEquals(countriesBeginningListSize, mockCountryService.mutableCountries.size());
        }
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCountriesList")
    void testDeleteCountryById(@NotNull List<Country> countries, String testName) {
        Integer countriesBeginningListSize = countries.size();
        MockCountryService mockCountryService = new MockCountryService(countries);
        CountryMutationDataFetcher countryMutationDataFetcher = new CountryMutationDataFetcher(mockCountryService);
        if (countriesBeginningListSize == 0) {
            UUID randomUUID = UUID.randomUUID();
            DeleteItemResponse deletedItemResponse = countryMutationDataFetcher.deleteCountryById(randomUUID);
            assertEquals(false, deletedItemResponse.success());
            assertEquals(String.format("unable to find country with the id %s", randomUUID), deletedItemResponse.message());
            assertEquals(null, deletedItemResponse.deletedItemId());
            assertEquals(countriesBeginningListSize, mockCountryService.mutableCountries.size());
        } else {
            Country existingCountry = countries.get(countriesBeginningListSize - 1);
            UUID existingCountryId = existingCountry.getId();
            DeleteItemResponse deletedItemResponse = countryMutationDataFetcher.deleteCountryById(existingCountryId);
            assertEquals(true, deletedItemResponse.success());
            assertEquals(String.format("'%s' has been successfully deleted.", existingCountry.getName()), deletedItemResponse.message());
            assertEquals(existingCountryId, deletedItemResponse.deletedItemId());
            assertEquals(countriesBeginningListSize-1, mockCountryService.mutableCountries.size());
        }
    }
}
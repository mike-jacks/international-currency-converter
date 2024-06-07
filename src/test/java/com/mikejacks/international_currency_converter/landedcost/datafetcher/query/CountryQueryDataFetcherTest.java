package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.service.impl.MockCountryService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CountryQueryDataFetcherTest {

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
    void testCountries(List<Country> expectedCountries, String testName) {
        MockCountryService countryService = new MockCountryService(expectedCountries);
        CountryQueryDataFetcher countryQueryDataFetcher = new CountryQueryDataFetcher(countryService);
        List<Country> result = countryQueryDataFetcher.countries();
        assertEquals(expectedCountries.size(), result.size());
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCountriesList")
    void testCountryById(List<Country> expectedCountries, String testName) {
        MockCountryService countryService = new MockCountryService(expectedCountries);
        CountryQueryDataFetcher countryQueryDataFetcher = new CountryQueryDataFetcher(countryService);
        if (expectedCountries.isEmpty()) {
            assertEquals(null, countryQueryDataFetcher.countryById(UUID.randomUUID()));
        } else {
            UUID expectedCountryId = expectedCountries.get(0).getId();
            Country results = countryQueryDataFetcher.countryById(expectedCountryId);
            assertEquals(expectedCountryId, results.getId());
        }
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCountriesList")
    void testCountryByName(List<Country> expectedCountries, String testName) {
        MockCountryService countryService = new MockCountryService(expectedCountries);
        CountryQueryDataFetcher countryQueryDataFetcher = new CountryQueryDataFetcher(countryService);
        if (expectedCountries.isEmpty()) {
            assertEquals(null, countryQueryDataFetcher.countryByName("Tatooine"));
        } else {
            String expectedCountryName = expectedCountries.get(0).getName();
            Country result = countryQueryDataFetcher.countryByName(expectedCountryName);
            assertEquals(expectedCountryName, result.getName());
        }
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCountriesList")
    void testCountryByCode(List<Country> expectedCountries, String testName) {
        MockCountryService countryService = new MockCountryService(expectedCountries);
        CountryQueryDataFetcher countryQueryDataFetcher = new CountryQueryDataFetcher(countryService);
        if (expectedCountries.isEmpty()) {
            assertEquals(null, countryQueryDataFetcher.countryByCode("TTO"));
        } else {
            String expectedCountryCode = expectedCountries.get(0).getCode();
            Country result = countryQueryDataFetcher.countryByCode(expectedCountryCode);
            assertEquals(expectedCountryCode, result.getCode());
        }
    }
}
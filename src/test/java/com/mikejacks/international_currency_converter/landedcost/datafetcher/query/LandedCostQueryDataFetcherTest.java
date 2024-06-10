package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;
import com.mikejacks.international_currency_converter.landedcost.service.impl.MockLandedCostService;
import com.mikejacks.international_currency_converter.localization.entity.Currency;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@code LandedCostQueryDataFetcher}.
 *
 * <p>This class tests the query methods of the {@code LandedCostQueryDataFetcher} class using mock data.</p>
 */
@ExtendWith(MockitoExtension.class)
class LandedCostQueryDataFetcherTest {

    /**
     * Provides mock data for parameterized tests.
     *
     * @return A stream of arguments containing mock data lists and test names.
     */
    private static @NotNull Stream<Arguments> mockData() {
        List<Currency> currencies = Arrays.asList(
                new Currency("COR", "TAT", 2.0), // Coruscant basecode
                new Currency("TAT", "COR", 0.5), // Tatooine basecode
                new Currency("TAT", "NAB", 1.5), // Tatooine basecode
                new Currency("NAB", "TAT", 0.6667), // Naboo basecode
                new Currency("NAB", "COR", 0.5), // Naboo basecode
                new Currency("COR", "NAB", 2.0) // Coruscant basecode
        );

        List<Product> products = Arrays.asList(
                new Product("LightSaber", 1000.00, "TAT"),
                new Product("ThermalDetonator", 100.00, "NAB"),
                new Product("Blaster", 300.00, "COR"),
                new Product("X-Wing", 49999.99, "NAB"),
                new Product("Aluminum Falcon", 99999.99, "TAT")
        );

        List<Country> countries = Arrays.asList(
                new Country("Coruscant", "COR", 5.0, 2.0),
                new Country("Naboo", "NAB", 4.0, 1.0),
                new Country("Hoth", "HOT", 3.0, 10.0),
                new Country("Endor", "END", 4.0, 5.0),
                new Country("Tatooine", "TAT", 4.0, 5.0)
        );

        return Stream.of(
                Arguments.of(currencies, products, countries, "Currencies, Products, and Countries List")
        );
    }

    /**
     * Tests the {@code calculateLandedCost} method of {@code LandedCostQueryDataFetcher}.
     *
     * @param expectedCurrencies The expected list of currencies.
     * @param expectedProducts The expected list of products.
     * @param expectedCountries The expected list of countries.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{3}")
    @MethodSource("mockData")
    void testCalculateLandedCost(List<Currency> expectedCurrencies, List<Product> expectedProducts, List<Country> expectedCountries, String testName) {
        MockLandedCostService landedCostService = new MockLandedCostService(expectedCurrencies, expectedProducts, expectedCountries);
        LandedCostQueryDataFetcher landedCostQueryDataFetcher = new LandedCostQueryDataFetcher(landedCostService);
        Currency currency = expectedCurrencies.getFirst();
        Product product = expectedProducts.getFirst();
        Country country = expectedCountries.getFirst();

        LandedCost landedCost = landedCostQueryDataFetcher.calculateLandedCost(product.getId(), country.getId(), currency.getTargetCode(), currency.getBaseCode());
        assertEquals(2140.0, landedCost.getTotalCost(), 0.01);
    }
}
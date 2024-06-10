package com.mikejacks.international_currency_converter.localization.datafetcher.query;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.service.impl.MockCurrencyService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@code CurrencyQueryDataFetcher}.
 *
 * <p>This class tests the various query methods of the {@code CurrencyQueryDataFetcher} class using mock data.</p>
 */
@ExtendWith(MockitoExtension.class)
class CurrencyQueryDataFetcherTest {

    /**
     * Provides mock currency lists for parameterized tests.
     *
     * @return A stream of arguments containing mock currency lists and test names.
     */
    private static @NotNull Stream<Arguments> mockCurrenciesLists() {
        return Stream.of(
                Arguments.of( Arrays.asList(
                        new Currency(UUID.randomUUID(), "COR", "TAT", 2.0), // Coruscant basecode
                        new Currency(UUID.randomUUID(), "TAT", "COR", 0.5), // Tatooine basecode
                        new Currency(UUID.randomUUID(), "TAT", "NAB", 1.5), // Tatooine basecode
                        new Currency(UUID.randomUUID(), "NAB", "TAT", 0.6667), // Naboo basecode
                        new Currency(UUID.randomUUID(), "NAB", "COR", 0.5), // Naboo basecode
                        new Currency(UUID.randomUUID(), "COR", "NAB", 2.0) // Coruscant basecode
                ),"Currencies List")
        );
    }

    /**
     * Tests the {@code currency} method of {@code CurrencyQueryDataFetcher}.
     *
     * @param expectedCurrencies The expected list of currencies.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCurrenciesLists")
    void testCurrency(List<Currency> expectedCurrencies, String testName) {
        MockCurrencyService currencyService = new MockCurrencyService(expectedCurrencies);
        CurrencyQueryDataFetcher currencyQueryDataFetcher = new CurrencyQueryDataFetcher(currencyService);
        Currency expectedCurrency = expectedCurrencies.get(0);
        Currency results = currencyQueryDataFetcher.currency(expectedCurrency.getBaseCode(), expectedCurrency.getTargetCode());
        assertEquals(Currency.class, results.getClass());
        assertEquals(expectedCurrency.getBaseCode(), results.getBaseCode());
        assertEquals(expectedCurrency.getTargetCode(), results.getTargetCode());
    }

    /**
     * Tests the {@code currencies} method of {@code CurrencyQueryDataFetcher}.
     *
     * @param expectedCurrencies The expected list of currencies.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCurrenciesLists")
    void testCurrencies(List<Currency> expectedCurrencies, String testName) {
        MockCurrencyService currencyService = new MockCurrencyService(expectedCurrencies);
        CurrencyQueryDataFetcher currencyQueryDataFetcher = new CurrencyQueryDataFetcher(currencyService);
        List<Currency> results = currencyQueryDataFetcher.currencies();
        assertEquals(Currency.class, results.get(0).getClass());
        assertEquals(expectedCurrencies.get(0).getId(), results.get(0).getId());
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCurrenciesLists")
    void testCurrenciesBy(List<Currency> expectedCurrencies, String testName) {
        MockCurrencyService currencyService = new MockCurrencyService(expectedCurrencies);
        CurrencyQueryDataFetcher currencyQueryDataFetcher = new CurrencyQueryDataFetcher(currencyService);

        List<Currency> results = currencyQueryDataFetcher.currenciesBy("COR", null);
        assertEquals(Currency.class, results.get(0).getClass());
        assertEquals(2, results.size());

        results = currencyQueryDataFetcher.currenciesBy(null, null);

        assertEquals(expectedCurrencies.size(), results.size());

    }

    /**
     * Tests the {@code currenciesByBaseCode} method of {@code CurrencyQueryDataFetcher}.
     *
     * @param expectedCurrencies The expected list of currencies.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCurrenciesLists")
    void testCurrenciesByBaseCode(List<Currency> expectedCurrencies, String testName) {
        MockCurrencyService currencyService = new MockCurrencyService(expectedCurrencies);
        CurrencyQueryDataFetcher currencyQueryDataFetcher = new CurrencyQueryDataFetcher(currencyService);
        List<Currency> results = currencyQueryDataFetcher.currenciesByBaseCode(expectedCurrencies.get(0).getBaseCode());
        assertEquals(Currency.class, results.get(0).getClass());
        assertEquals(expectedCurrencies.get(0).getId(), results.get(0).getId());
        assertEquals(2, results.size());
    }

    /**
     * Tests the {@code currenciesByTargetCode} method of {@code CurrencyQueryDataFetcher}.
     *
     * @param expectedCurrencies The expected list of currencies.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCurrenciesLists")
    void testCurrenciesByTargetCode(List<Currency> expectedCurrencies, String testName) {
        MockCurrencyService currencyService = new MockCurrencyService(expectedCurrencies);
        CurrencyQueryDataFetcher currencyQueryDataFetcher = new CurrencyQueryDataFetcher(currencyService);
        List<Currency> results = currencyQueryDataFetcher.currenciesByTargetCode(expectedCurrencies.get(0).getTargetCode());
        assertEquals(Currency.class, results.get(0).getClass());
        assertEquals(expectedCurrencies.get(0).getId(), results.get(0).getId());
        assertEquals(2, results.size());
    }




}
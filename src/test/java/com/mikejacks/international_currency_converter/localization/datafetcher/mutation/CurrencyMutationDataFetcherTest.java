package com.mikejacks.international_currency_converter.localization.datafetcher.mutation;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.model.CurrencyCreateInput;
import com.mikejacks.international_currency_converter.localization.model.CurrencyUpdateInput;
import com.mikejacks.international_currency_converter.localization.service.impl.MockCurrencyService;
import com.netflix.graphql.dgs.DgsComponent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@code CurrencyMutationDataFetcher}.
 *
 * <p>This class tests the mutation methods of the {@code CurrencyMutationDataFetcher} class using mock data.</p>
 */
@DgsComponent
public class CurrencyMutationDataFetcherTest {

    /**
     * Provides mock currency lists for parameterized tests.
     *
     * @return A stream of arguments containing mock currency lists and test names.
     */
    private static @NotNull Stream<Arguments> mockCurrenciesLists() {
        return Stream.of(
                Arguments.of( Arrays.asList(
                        new Currency("COR", "TAT", 2.0), // Coruscant basecode
                        new Currency("TAT", "COR", 0.5), // Tatooine basecode
                        new Currency("TAT", "NAB", 1.5), // Tatooine basecode
                        new Currency("NAB", "TAT", 0.6667), // Naboo basecode
                        new Currency("NAB", "COR", 0.5), // Naboo basecode
                        new Currency("COR", "NAB", 2.0) // Coruscant basecode
                ),"Currencies List")
        );
    }


    /**
     * Tests the {@code addCurrency} method of {@code CurrencyMutationDataFetcher}.
     *
     * @param currencies The expected list of currencies.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCurrenciesLists")
    void testAddCurrency(List<Currency> currencies, String testName) {
        MockCurrencyService currencyService = new MockCurrencyService(currencies);
        CurrencyMutationDataFetcher currencyMutationDataFetcher = new CurrencyMutationDataFetcher(currencyService);

        CurrencyCreateInput newCurrency = new CurrencyCreateInput("NAB", "HOT", 3.0);
        Currency addedCurrency = currencyMutationDataFetcher.addCurrency(newCurrency);

        assertEquals(newCurrency.getBaseCode(), addedCurrency.getBaseCode());
        assertEquals(newCurrency.getTargetCode(), addedCurrency.getTargetCode());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> currencyMutationDataFetcher.addCurrency(newCurrency));
        assertEquals("Currency wth base code: " + newCurrency.getBaseCode() + ", and target code:" + newCurrency.getTargetCode() + " already exists.", exception.getMessage());
        assertEquals(currencies.size() + 1, currencyService.mutableCurrencies.size());
    }

    /**
     * Tests the {@code updateCurrencyById} method of {@code CurrencyMutationDataFetcher}.
     *
     * @param currencies The expected list of currencies.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCurrenciesLists")
    void testUpdateCurrencyById(List<Currency> currencies, String testName) {
        MockCurrencyService currencyService = new MockCurrencyService(currencies);
        CurrencyMutationDataFetcher currencyMutationDataFetcher = new CurrencyMutationDataFetcher(currencyService);

        CurrencyUpdateInput updateCurrency = new CurrencyUpdateInput("NAB", "HOT", 3.0);
        UUID existingCurrencyId = currencies.getFirst().getId();
        Currency updatedCurrency = currencyMutationDataFetcher.updateCurrencyById(existingCurrencyId, updateCurrency);

        assertEquals(updateCurrency.getBaseCode(), updatedCurrency.getBaseCode());
        assertEquals(updateCurrency.getTargetCode(), updatedCurrency.getTargetCode());
        assertEquals(updateCurrency.getConversionRate(), updatedCurrency.getConversionRate());
    }
}

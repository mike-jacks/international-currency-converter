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

@ExtendWith(MockitoExtension.class)
class CurrencyQueryDataFetcherTest {

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

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockCurrenciesLists")
    void testGetCurrency(List<Currency> expectedCurrencies, String testName) {
        MockCurrencyService currencyService = new MockCurrencyService(expectedCurrencies);
        CurrencyQueryDataFetcher currencyQueryDataFetcher = new CurrencyQueryDataFetcher(currencyService);
        Currency expectedCurrency = expectedCurrencies.get(0);
        Currency results = currencyQueryDataFetcher.getCurrency(expectedCurrency.getBaseCode(), expectedCurrency.getTargetCode());
        assertEquals(Currency.class, results.getClass());
        assertEquals(expectedCurrency.getBaseCode(), results.getBaseCode());
        assertEquals(expectedCurrency.getTargetCode(), results.getTargetCode());
    }
}
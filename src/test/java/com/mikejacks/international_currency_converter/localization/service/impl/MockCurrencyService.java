package com.mikejacks.international_currency_converter.localization.service.impl;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.service.CurrencyService;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockCurrencyService implements CurrencyService {
    public List<Currency> currencies;
    public ArrayList<Currency> mutableCurrencies;

    public MockCurrencyService(List<Currency> currencies) {
        this.currencies = currencies;
        this.mutableCurrencies = new ArrayList<>();
        for (Currency currency : currencies) {
            this.mutableCurrencies.add(new Currency(currency.getId(), currency.getBaseCode(), currency.getTargetCode(), currency.getConversionRate()));
        }
    }

    /**
     * @param baseCode
     * @param targetCode
     * @return
     */
    @Override
    public Currency getCurrency(@NotNull String baseCode, @NotNull  String targetCode) {
        return currencies.stream().filter(currency -> currency.getBaseCode().equals(baseCode) && currency.getTargetCode().equals(targetCode)).findFirst().orElse(null);
    }
}

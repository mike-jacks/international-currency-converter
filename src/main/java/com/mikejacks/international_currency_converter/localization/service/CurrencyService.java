package com.mikejacks.international_currency_converter.localization.service;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.model.CurrencyCreateInput;
import com.mikejacks.international_currency_converter.localization.model.CurrencyUpdateInput;

import java.util.List;
import java.util.UUID;

public interface CurrencyService {
    List<Currency> currencies();
    Currency currency(String baseCode, String targetCode);
    List<Currency> currencies(String baseCode, String targetCode);
    Currency addCurrency(CurrencyCreateInput currency);
    Currency updateCurrencyById(UUID currencyId, CurrencyUpdateInput currency);
    Currency updateCurrencyRateToLiveById(UUID currencyId);
}

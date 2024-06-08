package com.mikejacks.international_currency_converter.localization.service;

import com.mikejacks.international_currency_converter.localization.entity.Currency;

public interface CurrencyService {
    Currency getCurrency(String baseCode, String targetCode);
}

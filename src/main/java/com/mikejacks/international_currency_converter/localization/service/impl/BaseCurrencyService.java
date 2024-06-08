package com.mikejacks.international_currency_converter.localization.service.impl;

import com.mikejacks.international_currency_converter.localization.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.repository.CurrencyRepository;

@Service
public class BaseCurrencyService implements CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Override public Currency getCurrency(String baseCode, String targetCode) {
        return currencyRepository.findByBaseCodeAndTargetCode(baseCode, targetCode);
    }

}

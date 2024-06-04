package com.mikejacks.international_currency_converter.localization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.repository.CurrencyRepository;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency getCurrency(String baseCode, String targetCode) {
        return currencyRepository.findByBaseCodeAndTargetCode(baseCode, targetCode);
    }

}

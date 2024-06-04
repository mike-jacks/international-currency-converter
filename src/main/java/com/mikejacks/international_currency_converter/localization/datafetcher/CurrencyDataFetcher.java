package com.mikejacks.international_currency_converter.localization.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.service.CurrencyService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class CurrencyDataFetcher {
    @Autowired
    private CurrencyService currencyService;

    @DgsQuery
    public Currency getCurrency(String baseCode, String targetCode) {
        return currencyService.getCurrency(baseCode, targetCode);
    }

}

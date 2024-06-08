package com.mikejacks.international_currency_converter.localization.datafetcher.query;

import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.service.CurrencyService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class CurrencyQueryDataFetcher {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyQueryDataFetcher(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @DgsQuery
    public Currency getCurrency(@InputArgument final String baseCode, @InputArgument final String targetCode) {
        return currencyService.getCurrency(baseCode, targetCode);
    }

}

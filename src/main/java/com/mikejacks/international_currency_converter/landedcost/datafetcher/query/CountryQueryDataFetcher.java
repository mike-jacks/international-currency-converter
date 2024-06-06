package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.service.CountryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class CountryQueryDataFetcher {
    private CountryService countryService;

    @Autowired
    public CountryQueryDataFetcher(final CountryService countryService) {
        this.countryService = countryService;
    }

    @DgsQuery
    public List<Country> countries() {
        return countryService.countries();
    }

    @DgsQuery
    public Country countryById(final @InputArgument UUID countryId) {
        return countryService.countryById(countryId);
    }

    @DgsQuery
    public Country countryByName(final @InputArgument String name) {
        return countryService.countryByName(name);
    }

    @DgsQuery
    public Country countryByCode(final @InputArgument String code) {
        return countryService.countryByCode(code);
    }

}
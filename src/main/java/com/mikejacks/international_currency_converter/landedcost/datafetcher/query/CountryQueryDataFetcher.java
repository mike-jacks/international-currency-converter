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
    private final CountryService countryService;

    @Autowired
    public CountryQueryDataFetcher(final CountryService countryService) {
        this.countryService = countryService;
    }

    @DgsQuery
    public List<Country> countries() {
        return countryService.countries();
    }

    @DgsQuery
    public Country country(@InputArgument final UUID countryId, @InputArgument final String name, @InputArgument final String code) {
        return countryService.country(countryId, name, code);
    }

    @DgsQuery
    public Country countryById(@InputArgument final UUID countryId) {
        return countryService.country(countryId, null, null);
    }

    @DgsQuery
    public Country countryByName(@InputArgument final String name) {
        return countryService.country(null, name, null);
    }

    @DgsQuery
    public Country countryByCode(@InputArgument final String code) {
        return countryService.country(null, null, code);
    }

}
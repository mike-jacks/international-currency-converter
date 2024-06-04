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
    public CountryQueryDataFetcher(CountryService countryService) {
        this.countryService = countryService;
    }
    @DgsQuery
    public List<Country> countries() {
        System.out.println("Fetching all countries");
        return countryService.countries();
    }

    @DgsQuery
    public Country countryById(@InputArgument UUID countryId) {
        return countryService.countryById(countryId);
    }

    @DgsQuery
    public Country countryByName(@InputArgument String name) {
        return countryService.countryByName(name);
    }

    @DgsQuery
    public Country countryByCode(@InputArgument String code) {
        return countryService.countryByCode(code);
    }

}

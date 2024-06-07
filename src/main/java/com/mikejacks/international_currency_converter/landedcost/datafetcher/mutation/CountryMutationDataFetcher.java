package com.mikejacks.international_currency_converter.landedcost.datafetcher.mutation;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.service.CountryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@DgsComponent
public class CountryMutationDataFetcher {
    private final CountryService countryService;

    @Autowired
    public CountryMutationDataFetcher(CountryService countryService) {
        this.countryService = countryService;
    }

    @DgsMutation
    public Country addCountry(@InputArgument CountryCreateInput country) {
        return countryService.addCountry(country);
    }

    @DgsMutation
    public Country updateCountryById(@InputArgument UUID id, @InputArgument CountryUpdateInput country) {
        return countryService.updateCountry(id, null, country);
    }

    @DgsMutation
    public Country updateCountryByName(@InputArgument String name, @InputArgument CountryUpdateInput country) {
        return countryService.updateCountry(null, name, country);
    }

    @DgsMutation
    public DeleteItemResponse deleteCountryById(@InputArgument UUID id) {
        return countryService.deleteCountryById(id);
    }
}

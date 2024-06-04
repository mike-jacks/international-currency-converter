package com.mikejacks.international_currency_converter.landedcost.datafetcher;

import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;
import com.mikejacks.international_currency_converter.landedcost.service.LandedCostService;
import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class LandedCostDataFetcher {
    @Autowired
    private LandedCostService landedCostService;

    @DgsQuery
    public LandedCost calculateLandedCost(@InputArgument UUID productId, @InputArgument UUID countryId, @InputArgument String targetCurrencyCode, @InputArgument String baseCurrencyCode) {
        return landedCostService.calculateLandedCost(productId, countryId, targetCurrencyCode, baseCurrencyCode);
    }

    @DgsQuery
    public List<Country> countries() {
        return landedCostService.countries();
    }

    @DgsQuery
    public Country countryById(@InputArgument UUID countryId) {
        return landedCostService.countryById(countryId);
    }

    @DgsQuery
    public Country countryByName(@InputArgument String name) {
        return landedCostService.countryByName(name);
    }

    @DgsQuery
    public Country countryByCode(@InputArgument String code) {
        return landedCostService.countryByCode(code);
    }
}

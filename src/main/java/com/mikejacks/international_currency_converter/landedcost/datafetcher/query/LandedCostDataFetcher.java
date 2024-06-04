package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;
import com.mikejacks.international_currency_converter.landedcost.service.LandedCostService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@DgsComponent
public class LandedCostDataFetcher {

    private LandedCostService landedCostService;

    @Autowired
    public LandedCostDataFetcher(LandedCostService landedCostService) {
        this.landedCostService = landedCostService;
    }

    @DgsQuery
    public LandedCost calculateLandedCost(@InputArgument UUID productId, @InputArgument UUID countryId, @InputArgument String targetCurrencyCode, @InputArgument String baseCurrencyCode) {
        return landedCostService.calculateLandedCost(productId, countryId, targetCurrencyCode, baseCurrencyCode);
    }


}

package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;
import com.mikejacks.international_currency_converter.landedcost.service.LandedCostService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Data fetcher for landed cost queries in the GraphQL API.
 *
 * <p>This class handles the GraphQL queries related to calculating the landed cost of a product in a specified country,
 * utilizing the {@code LandedCostService} to perform the calculations.</p>
 */
@DgsComponent
public class LandedCostQueryDataFetcher {

    private LandedCostService landedCostService;

    /**
     * Constructs a new {@code LandedCostQueryDataFetcher} with the specified {@code LandedCostService}.
     *
     * @param landedCostService The service used to handle landed cost calculations.
     */
    @Autowired
    public LandedCostQueryDataFetcher(LandedCostService landedCostService) {
        this.landedCostService = landedCostService;
    }

    /**
     * Calculates the landed cost of a product in a specified country with the given currency codes.
     *
     * <p>This method retrieves the product and country information based on their UUIDs,
     * and calculates the landed cost considering the specified target and base currency codes.</p>
     *
     * @param productId The unique ID of the product. Must not be null.
     * @param countryId The unique ID of the country. Must not be null.
     * @param targetCurrencyCode The currency code to which the landed cost should be converted. Must not be null.
     * @param baseCurrencyCode The base currency code of the product. Must not be null.
     * @return The {@code LandedCost} object containing the total cost.
     */
    @DgsQuery
    public LandedCost calculateLandedCost(@InputArgument UUID productId, @InputArgument UUID countryId, @InputArgument String targetCurrencyCode, @InputArgument String baseCurrencyCode) {
        return landedCostService.calculateLandedCost(productId, countryId, targetCurrencyCode, baseCurrencyCode);
    }


}

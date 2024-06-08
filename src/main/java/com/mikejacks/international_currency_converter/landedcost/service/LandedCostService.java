package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.model.LandedCost;

import java.util.UUID;

public interface LandedCostService {
    LandedCost calculateLandedCost(UUID productId, UUID countryId, String targetCurrencyCode, String baseCurrencyCode);
}

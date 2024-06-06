package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface CountryService {
    // Query Services
    List<Country> countries();

    Country countryById(@NotNull UUID countryId);

    Country countryByName(@NotNull String name);

    Country countryByCode(@NotNull String code);

    // Mutation Services
    Country addCountry(@NotNull CountryCreateInput countryCreateInput);

    Country updateCountry(UUID countryId, String name, @NotNull CountryUpdateInput country);

    DeleteItemResponse deleteCountryById(@NotNull UUID countryId);
}

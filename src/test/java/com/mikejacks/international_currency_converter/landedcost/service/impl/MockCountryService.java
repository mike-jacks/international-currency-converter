package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.service.CountryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MockCountryService implements CountryService {

    public List<Country> countries;

    public ArrayList<Country> mutableCountries;

    public MockCountryService(List<Country> countries) {
       this.countries = countries;
       this.mutableCountries = new ArrayList<>();
       for (Country country : countries) {
           this.mutableCountries.add(new Country(country.getId(), country.getName(), country.getCode(), country.getDutyRate(), country.getTaxRate()));
       }
    }

    @Override
    public List<Country> countries() {
        return this.countries;
    }

    @Override
    public Country country(UUID countryId, String name, String code) {
        if (countryId != null && name == null && code == null) {
            return countries.stream().filter((country) -> country.getId().equals(countryId)).findFirst().orElse(null);
        }
        if (countryId == null && name != null && code == null) {
            return countries.stream().filter((country) -> country.getName().equals(name)).findFirst().orElse(null);
        }
        if (countryId == null && name == null && code != null) {
            return countries.stream().filter((country) -> country.getCode().equals(code)).findFirst().orElse(null);
        }
        if (countryId == null && name == null && code == null) {
            throw new IllegalArgumentException("No arguments present. You must have only one of the following arguments: countryId, name, or code");
        } else {
            throw new IllegalArgumentException("Too many arguments present. You must have only one of the following arguments: countryId, name, or code");
        }
    }


    @Override
    public Country addCountry(@NotNull CountryCreateInput countryCreateInput) {
        Country newCountry = new Country(countryCreateInput.getName(), countryCreateInput.getCode(), countryCreateInput.getDutyRate(), countryCreateInput.getTaxRate());
        mutableCountries.add(newCountry);
        return newCountry;
    }


    @Override
    public Country updateCountry(UUID countryId, String name, CountryUpdateInput countryUpdateInput) {
        Country existingCountry = null;
        if (countryId != null && name != null) {
            throw new IllegalArgumentException("only a countryId or a name must be provided. Not both.");
        } else if (countryId != null) {
            for (Country country : mutableCountries) {
               if (country.getId().equals(countryId)) {
                   existingCountry = country;
               }
            }
           if (existingCountry == null) {
               throw new IllegalArgumentException("Country with id " + countryId + " not found.");
           }
        } else if (name != null) {
            for (Country country : mutableCountries) {
                if (country.getName().equals(name)) {
                    existingCountry = country;
                }
            }
            if (existingCountry == null) {
                throw new IllegalArgumentException("Country with name " + name + " not found.");
            }
        } else {
            throw new IllegalArgumentException("either a countryId or name must be provided");
        }
        if (countryUpdateInput.getName() != null) {
            existingCountry.setName(countryUpdateInput.getName());
        }
        if (countryUpdateInput.getCode() != null) {
            existingCountry.setCode(countryUpdateInput.getCode());
        }
        if (countryUpdateInput.getDutyRate() != null) {
            existingCountry.setDutyRate(countryUpdateInput.getDutyRate());
        }
        if (countryUpdateInput.getTaxRate() != null) {
            existingCountry.setTaxRate(countryUpdateInput.getTaxRate());
        }
        return existingCountry;
    }

    @Override
    public DeleteItemResponse deleteCountryById(UUID countryId) {
        Country existingCountry = null;
        if (countryId != null) {
            for (Country country : mutableCountries) {
                if (country.getId().equals(countryId)) {
                    existingCountry = country;
                }
            }
            if (existingCountry == null) {
                return new DeleteItemResponse(false, String.format("unable to find country with the id %s", countryId), null);
            }
        }
        mutableCountries.remove(existingCountry);
        return new DeleteItemResponse(true, String.format("'%s' has been successfully deleted.", existingCountry.getName()), countryId);
    }
}

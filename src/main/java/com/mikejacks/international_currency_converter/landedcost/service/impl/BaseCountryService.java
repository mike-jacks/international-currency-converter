package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.repository.CountryRepository;
import com.mikejacks.international_currency_converter.landedcost.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class BaseCountryService implements CountryService {

    private final CountryRepository countryRepository;


    @Autowired
    public BaseCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // Query Services
    @Override public List<Country> countries() {
        return countryRepository.findAll();
    }

    @Override public Country countryById(final @NotNull UUID countryId) {
        return countryRepository.findById(countryId).orElse(null);
    }

    @Override public Country countryByName(final @NotNull String name) {
        return countryRepository.findCountryByName(name).orElse(null);
    }

    @Override public Country countryByCode(final @NotNull String code) {
        return countryRepository.findCountryByCode(code).orElseThrow();
    }


    // Mutation Services
    @Override public Country addCountry(final @NotNull CountryCreateInput countryCreateInput) {
        System.out.println("Adding country: " + countryCreateInput);
        Country newCountry = new Country(
                countryCreateInput.getName(),
                countryCreateInput.getCode(),
                countryCreateInput.getDutyRate(),
                countryCreateInput.getTaxRate()
        );
        Country savedCountry = countryRepository.save(newCountry);
        System.out.println("Country saved: " + savedCountry);
        return savedCountry;
    }

    @Override public Country updateCountry(final UUID countryId, final String name, final @NotNull CountryUpdateInput country) {
        Country existingCountry;
        if (countryId != null && name != null) {
           throw new IllegalArgumentException("only a countryId or a name must be provided. Not both.");
        }else if (countryId != null) {
            existingCountry = countryRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country with id " + countryId + " not found."));
        } else if (name != null) {
            existingCountry = countryRepository.findCountryByName(name).orElseThrow(() -> new RuntimeException("Country with name " + name + " not found."));
        } else throw new IllegalArgumentException("either a countryId or a name must be provided");

        if (country.getName() != null) {
            existingCountry.setName(country.getName());
        }
        if (country.getCode() != null) {
            existingCountry.setCode(country.getCode());
        }
        if (country.getDutyRate() != null) {
            existingCountry.setDutyRate(country.getDutyRate());
        }
        if (country.getTaxRate() != null) {
            existingCountry.setTaxRate(country.getTaxRate());
        }
        return countryRepository.save(existingCountry);
    }

    @Override public DeleteItemResponse deleteCountryById(final @NotNull UUID countryId) {
       Optional<Country> existingCountryOptional = countryRepository.findById(countryId);
       if (existingCountryOptional.isEmpty()) {
           return new DeleteItemResponse(false, String.format("unable to find country with the id %s", countryId), null);
       }
       Country existingCountry = existingCountryOptional.get();
       countryRepository.delete(existingCountry);
       return new DeleteItemResponse(true, String.format("'%s' has been successfully deleted.", existingCountry.getName()), countryId);
    }
}

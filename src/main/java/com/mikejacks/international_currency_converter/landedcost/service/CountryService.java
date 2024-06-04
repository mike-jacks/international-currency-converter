package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.landedcost.model.CountryCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.CountryUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class CountryService {

    private CountryRepository countryRepository;

    // Query Services
    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> countries() {
        return countryRepository.findAll();
    }

    public Country countryById(UUID countryId) {
        return countryRepository.findById(countryId).orElse(null);
    }

    public Country countryByName(String name) {
        return countryRepository.findByName(name).orElseThrow();
    }

    public Country countryByCode(String code) {
        return countryRepository.findByCode(code).orElseThrow();
    }


    // Mutation Services
    public Country addCountry(CountryCreateInput countryCreateInput) {
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

    public Country updateCountryById(UUID countryId, CountryUpdateInput country) {
        Country existingCountry = countryRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country not found"));
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

    public Country updateCountryByName(String name, CountryUpdateInput country) {
        Country existingCountry = countryRepository.findByName(name).orElseThrow(() -> new RuntimeException("Country not found"));
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

    public DeleteItemResponse deleteCountryById(UUID countryId) {
       Optional<Country> existingCountryOptional = countryRepository.findById(countryId);
       if (existingCountryOptional.isEmpty()) {
           return new DeleteItemResponse(false, String.format("unable to find country with the id %s", countryId), null);
       }
       Country existingCountry = existingCountryOptional.get();
       countryRepository.delete(existingCountry);
       return new DeleteItemResponse(true, String.format("'%s' has been successfully deleted.", existingCountry.getName()), countryId);
    }
}

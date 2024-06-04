package com.mikejacks.international_currency_converter.landedcost.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findByName(String name);
    Optional<Country> findByCode(String code);

}

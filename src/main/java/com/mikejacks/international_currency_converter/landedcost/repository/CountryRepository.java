package com.mikejacks.international_currency_converter.landedcost.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;

/**
 * Repository interface for accessing Country data.
 *
 * <p>This interface provides various methods to query and manage {@code Country} entities in the database.
 * It extends the {@code JpaRepository} interface provided by Spring Data JPA.</p>
 */
public interface CountryRepository extends JpaRepository<Country, UUID> {
    /**
     * Finds a country by its ID.
     *
     * @param id The unique identifier of the country.
     * @return An {@code Optional} containing the found country, or {@code Optional.empty()} if no country is found.
     */
    Optional<Country> findCountryById(UUID id);

    /**
     * Finds a country by its name.
     *
     * @param name The name of the country.
     * @return An {@code Optional} containing the found country, or {@code Optional.empty()} if no country is found.
     */
    Optional<Country> findCountryByName(String name);

    /**
     * Finds a country by its code.
     *
     * @param code The code of the country.
     * @return An {@code Optional} containing the found country, or {@code Optional.empty()} if no country is found.
     */
    Optional<Country> findCountryByCode(String code);

}

package com.mikejacks.international_currency_converter.localization.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.localization.entity.Currency;

/**
 * Repository interface for managing {@link Currency} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations and custom queries
 * for the {@code Currency} entity. The repository is responsible for accessing and manipulating
 * currency data in the database.</p>
 *
 * <p>The following custom query methods are defined:</p>
 * <ul>
 *     <li>{@link #findCurrenciesByBaseCodeAndTargetCode(String, String)}: Retrieves a {@code Currency} based on the base code and target code.</li>
 *     <li>{@link #findCurrenciesByBaseCode(String)}: Retrieves currencies that have the specified base code.</li>
 *     <li>{@link #findCurrenciesByTargetCode(String)}: Retrieves currencies that have the specified target code.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CurrencyRepository currencyRepository = ...;
 * Optional<Currency> currency = currencyRepository.findCurrenciesByBaseCodeAndTargetCode("USD", "EUR");
 * if (currency.isPresent()) {
 *     // Handle the found currency
 * } else {
 *     // Handle the case where the currency is not found
 * }
 * }</pre>
 *
 * @see JpaRepository
 * @see Currency
 */
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    /**
     * Retrieves a {@link Currency} based on the specified base code and target code.
     *
     * @param baseCode The base code of the currency. Must not be null.
     * @param targetCode The target code of the currency. Must not be null.
     * @return An {@code Optional} containing the found {@code Currency}, or {@code Optional.empty()} if no currency is found.
     */
    Optional<Currency> findCurrenciesByBaseCodeAndTargetCode(String baseCode, String targetCode);

    /**
     * Retrieves currencies that have the specified base code.
     *
     * @param baseCode The base code of the currencies to retrieve. Must not be null.
     * @return An {@code Optional} containing the found {@code Currency}, or {@code Optional.empty()} if no currency is found.
     */
    Optional<Currency> findCurrenciesByBaseCode(String baseCode);

    /**
     * Retrieves currencies that have the specified target code.
     *
     * @param targetCode The target code of the currencies to retrieve. Must not be null.
     * @return An {@code Optional} containing the found {@code Currency}, or {@code Optional.empty()} if no currency is found.
     */
    Optional<Currency> findCurrenciesByTargetCode(String targetCode);

}

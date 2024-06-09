package com.mikejacks.international_currency_converter.localization.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.localization.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    Optional<Currency> findCurrenciesByBaseCodeAndTargetCode(String baseCode, String targetCode);
    Optional<Currency> findCurrenciesByBaseCode(String baseCode);
    Optional<Currency> findCurrenciesByTargetCode(String targetCode);

}

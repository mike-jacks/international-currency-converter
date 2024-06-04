package com.mikejacks.international_currency_converter.localization.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.localization.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    Currency findByBaseCodeAndTargetCode(String baseCode, String targetCode);

}

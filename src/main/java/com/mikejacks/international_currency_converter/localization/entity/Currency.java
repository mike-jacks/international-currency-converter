package com.mikejacks.international_currency_converter.localization.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a currency entity with a unique ID, base code, target code, and conversion rate.
 *
 * <p>This class is used to map currency data to a database table and provides methods for
 * getting and setting the currency attributes. The currency codes are validated to ensure
 * they are exactly 3 characters long and contain only letters.</p>
 */
@Entity
public class Currency {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(length = 3)
    private String baseCode;

    @Column(length = 3)
    private String targetCode;

    @Column()
    private Double conversionRate;

    /**
     * Default constructor. Generates a new unique ID.
     */
    public Currency() {
        this.id = UUID.randomUUID();
    }

    /**
     * Constructs a new {@code Currency} with the specified ID, base code, target code, and conversion rate.
     *
     * @param id The unique ID of the currency.
     * @param baseCode The base currency code. Must be exactly 3 letters.
     * @param targetCode The target currency code. Must be exactly 3 letters.
     * @param conversionRate The conversion rate between the base and target currencies.
     */
    public Currency(UUID id, String baseCode, String targetCode, Double conversionRate) {
        this.setId(id);
        this.setBaseCode(baseCode);
        this.setTargetCode(targetCode);
        this.setConversionRate(conversionRate);
    }

    /**
     * Constructs a new {@code Currency} with the specified base code, target code, and conversion rate.
     * Generates a new unique ID.
     *
     * @param baseCode The base currency code. Must be exactly 3 letters.
     * @param targetCode The target currency code. Must be exactly 3 letters.
     * @param conversionRate The conversion rate between the base and target currencies.
     */
    public Currency(String baseCode, String targetCode, Double conversionRate) {
        this(UUID.randomUUID(), baseCode, targetCode, conversionRate);
    }

    /**
     * Gets the unique ID of the currency.
     *
     * @return The unique ID.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique ID of the currency.
     *
     * @param id The unique ID to set.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the base currency code.
     *
     * @return The base currency code.
     */
    public String getBaseCode() {
        return baseCode;
    }

    /**
     * Sets the base currency code.
     *
     * @param baseCode The base currency code. Must be exactly 3 letters.
     * @throws IllegalArgumentException if the base code is null, not 3 characters long, or contains non-letter characters.
     */
    public void setBaseCode(@NotNull String baseCode) {
        if  (!"[A-Za-z]{3}".matches(baseCode.trim())) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.baseCode = baseCode.trim().toUpperCase();
    }

    /**
     * Gets the target currency code.
     *
     * @return The target currency code.
     */
    public String getTargetCode() {
        return targetCode;
    }

    /**
     * Sets the target currency code.
     *
     * @param targetCode The target currency code. Must be exactly 3 letters.
     * @throws IllegalArgumentException if the target code is null, not 3 characters long, or contains non-letter characters.
     */
    public void setTargetCode(@NotNull String targetCode) {
        if  (!"[A-Za-z]{3}".matches(targetCode.trim())) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.targetCode = targetCode.trim().toUpperCase();
    }

    /**
     * Gets the conversion rate between the base and target currencies.
     *
     * @return The conversion rate.
     */
    public Double getConversionRate() {
        return conversionRate;
    }

    /**
     * Sets the conversion rate between the base and target currencies.
     *
     * @param conversionRate The conversion rate.
     */
    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", baseCode='" + baseCode + '\'' +
                ", targetCode='" + targetCode + '\'' +
                ", conversionRate=" + conversionRate +
                '}';
    }
}

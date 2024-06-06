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

    @Column(nullable = false, length = 3)
    private String baseCode;

    @Column(nullable = false, length = 3)
    private String targetCode;

    @Column(nullable = false)
    private Double conversionRate;

    public Currency() {
        this.id = UUID.randomUUID();
    }

    public Currency(UUID id, String baseCode, String targetCode, Double conversionRate) {
        this.setId(id);
        this.setBaseCode(baseCode);
        this.setTargetCode(targetCode);
        this.setConversionRate(conversionRate);
    }

    public Currency(String baseCode, String targetCode, Double conversionRate) {
        this(UUID.randomUUID(), baseCode, targetCode, conversionRate);
    }

    /**
     * @return UUID return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return String return the baseCode
     */
    public String getBaseCode() {
        return baseCode;
    }

    /**
     * @param baseCode the baseCode to set
     */
    public void setBaseCode(String baseCode) {
        if  (baseCode == null || !baseCode.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.baseCode = baseCode.trim().toUpperCase();
    }

    /**
     * @return String return the targetCode
     */
    public String getTargetCode() {
        return targetCode;
    }

    /**
     * @param targetCode the targetCode to set
     */
    public void setTargetCode(String targetCode) {
        if  (targetCode == null || !targetCode.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.targetCode = targetCode.trim().toUpperCase();
    }

    /**
     * @return Double return the conversionRate
     */
    public Double getConversionRate() {
        return conversionRate;
    }

    /**
     * @param conversionRate the conversionRate to set
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

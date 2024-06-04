package com.mikejacks.international_currency_converter.landedcost.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Country {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 3)
    private String code;
    @Column(nullable = false)
    private Double dutyRate;
    @Column(nullable = false)
    private Double taxRate;

    public Country() {
    }

    public Country(UUID id, String name, String code, Double dutyRate, Double taxRate) {
        this.setId(id);
        this.setName(name);
        this.setCode(code);
        this.setDutyRate(dutyRate);
        this.setTaxRate(taxRate);
    }

    public Country(String name, String code, Double dutyRate, Double taxRate) {
        this(UUID.randomUUID(), name, code, dutyRate, taxRate);
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
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Double return the dutyRate
     */
    public Double getDutyRate() {
        return dutyRate;
    }

    /**
     * @param dutyRate the dutyRate to set
     */
    public void setDutyRate(Double dutyRate) {
        this.dutyRate = dutyRate;
    }

    /**
     * @return Double return the taxRate
     */
    public Double getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return String return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        if  (code == null || !code.trim().matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 characters long and only include letters");
        }
        this.code = code.trim().toUpperCase();
    }
}

package com.mikejacks.international_currency_converter.landedcost.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}

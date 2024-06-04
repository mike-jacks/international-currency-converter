package com.mikejacks.international_currency_converter.landedcost.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByName(String name);
    List<Product> findAllByPriceLessThanEqual(Double maxPrice);
    List<Product> findAllByPriceGreaterThanEqual(Double minPrice);
    List<Product> findAllByPriceBetween(Double minPrice, Double maxPrice);
}

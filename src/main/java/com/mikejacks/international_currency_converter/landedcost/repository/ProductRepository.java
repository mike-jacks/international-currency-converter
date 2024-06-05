package com.mikejacks.international_currency_converter.landedcost.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Query Functions
    Optional<Product> findProductById(UUID id);
    Optional<Product> findProductByName(String name);
    List<Product> findProductsByPriceLessThanEqual(Double maxPrice);
    List<Product> findProductsByPriceGreaterThanEqual(Double minPrice);
    List<Product> findProductsByPriceBetween(Double minPrice, Double maxPrice);
}

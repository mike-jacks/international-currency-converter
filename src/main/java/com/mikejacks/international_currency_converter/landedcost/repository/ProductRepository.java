package com.mikejacks.international_currency_converter.landedcost.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;

/**
 * Repository interface for accessing Product data.
 *
 * <p>This interface provides various methods to query and manage {@code Product} entities in the database.
 * It extends the {@code JpaRepository} interface provided by Spring Data JPA.</p>
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Query Functions

    /**
     * Finds a product by its ID.
     *
     * @param id The unique identifier of the product.
     * @return An {@code Optional} containing the found product, or {@code Optional.empty()} if no product is found.
     */
    Optional<Product> findProductById(UUID id);

    /**
     * Finds a product by its name.
     *
     * @param name The name of the product.
     * @return An {@code Optional} containing the found product, or {@code Optional.empty()} if no product is found.
     */
    Optional<Product> findProductByName(String name);

    /**
     * Finds products with prices less than or equal to the specified maximum price.
     *
     * @param maxPrice The maximum price of the products to find.
     * @return A list of products with prices less than or equal to the specified maximum price.
     */
    List<Product> findProductsByPriceLessThanEqual(Double maxPrice);

    /**
     * Finds products with prices greater than or equal to the specified minimum price.
     *
     * @param minPrice The minimum price of the products to find.
     * @return A list of products with prices greater than or equal to the specified minimum price.
     */
    List<Product> findProductsByPriceGreaterThanEqual(Double minPrice);

    /**
     * Finds products with prices between the specified minimum and maximum prices.
     *
     * @param minPrice The minimum price of the products to find.
     * @param maxPrice The maximum price of the products to find.
     * @return A list of products with prices between the specified minimum and maximum prices.
     */
    List<Product> findProductsByPriceBetween(Double minPrice, Double maxPrice);
}

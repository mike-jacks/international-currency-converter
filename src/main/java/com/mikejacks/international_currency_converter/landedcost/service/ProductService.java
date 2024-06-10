package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing products.
 *
 * <p>This interface defines the contract for various operations related to products, including
 * querying, adding, updating, and deleting products.</p>
 */
public interface ProductService {
    // Query Services

    /**
     * Retrieves a list of all products.
     *
     * @return A list of all {@code Product} objects.
     */
    List<Product> products();

    /**
     * Retrieves a product based on the specified ID or name.
     *
     * <p>This method fetches a {@code Product} object from the repository based on the provided ID or name.
     * If both ID and name are provided, an {@code IllegalArgumentException} is thrown.
     * If a product with the specified ID or name is found, it is returned. Otherwise, {@code null} is returned.</p>
     *
     * @param id The ID of the product to retrieve. Can be null.
     * @param name The name of the product to retrieve. Can be null.
     * @return The {@code Product} object matching the specified ID or name, or {@code null} if no such product is found.
     * @throws IllegalArgumentException if both ID and name are specified.
     */
    Product product(UUID id, String name);

    /**
     * Retrieves a product based on the specified ID.
     *
     * <p>This method fetches a {@code Product} object from the repository based on the provided ID.
     * If a product with the specified ID is found, it is returned. Otherwise, {@code null} is returned.</p>
     *
     * @param id The ID of the product to retrieve. Must not be null.
     * @return The {@code Product} object matching the specified ID, or {@code null} if no such product is found.
     */
    Product productById(@NotNull UUID id);

    /**
     * Retrieves a product based on the specified name.
     *
     * <p>This method fetches a {@code Product} object from the repository based on the provided name.
     * If a product with the specified name is found, it is returned. Otherwise, {@code null} is returned.</p>
     *
     * @param name The name of the product to retrieve. Must not be null.
     * @return The {@code Product} object matching the specified name, or {@code null} if no such product is found.
     */
    Product productByName(@NotNull String name);

    /**
     * Retrieves a list of products with prices less than or equal to the specified maximum price.
     *
     * <p>This method fetches {@code Product} objects from the repository that have prices less than or equal to
     * the specified maximum price.</p>
     *
     * @param maxPrice The maximum price of the products to retrieve. Must not be null.
     * @return A list of {@code Product} objects with prices less than or equal to the specified maximum price.
     */
    List<Product> productsByPriceLessThanOrEqualTo(@NotNull Double maxPrice);

    /**
     * Retrieves a list of products with prices greater than or equal to the specified minimum price.
     *
     * <p>This method fetches {@code Product} objects from the repository that have prices greater than or equal to
     * the specified minimum price.</p>
     *
     * @param minPrice The minimum price of the products to retrieve. Must not be null.
     * @return A list of {@code Product} objects with prices greater than or equal to the specified minimum price.
     */
    List<Product> productsByPriceGreaterThanOrEqualTo(@NotNull Double minPrice);

    /**
     * Retrieves a list of products with prices between the specified minimum and maximum prices.
     *
     * <p>This method fetches {@code Product} objects from the repository that have prices between the specified
     * minimum and maximum prices.</p>
     *
     * @param minPrice The minimum price of the products to retrieve. Must not be null.
     * @param maxPrice The maximum price of the products to retrieve. Must not be null.
     * @return A list of {@code Product} objects with prices between the specified minimum and maximum prices.
     */
    List<Product> productsByPriceBetween(@NotNull Double minPrice, @NotNull Double maxPrice);

    // Mutation Services

    /**
     * Adds a new product to the repository.
     *
     * <p>This method creates a new {@code Product} object using the details provided in the {@code ProductCreateInput} object
     * and saves it to the repository.</p>
     *
     * @param productCreateInput A {@code ProductCreateInput} object containing the details of the new product to be added.
     * @return The newly added {@code Product} object.
     */
    Product addProduct(@NotNull ProductCreateInput productCreateInput);

    /**
     * Updates an existing product's details by its ID or name.
     *
     * <p>This method finds an existing {@code Product} object by its ID or name. If the product exists, it updates the name,
     * price, and currency code if they are provided in the {@code ProductUpdateInput} object. If the product does not exist,
     * an {@code IllegalArgumentException} is thrown. The updated product is then saved to the repository and returned.</p>
     *
     * @param id The ID of the product to be updated. Can be null.
     * @param name The name of the product to be updated. Can be null.
     * @param productUpdateInput A {@code ProductUpdateInput} object containing the updated details of the product. Must not be null.
     * @return The updated {@code Product} object.
     * @throws IllegalArgumentException if the product with the specified ID or name does not exist.
     */
    Product updateProduct(UUID id, String name, @NotNull ProductUpdateInput productUpdateInput);

    /**
     * Deletes a product by its ID.
     *
     * <p>This method finds an existing {@code Product} object by its ID and deletes it from the repository. If the product does not exist,
     * a {@code DeleteItemResponse} indicating failure is returned. Otherwise, a {@code DeleteItemResponse} indicating success is returned.</p>
     *
     * @param productId The ID of the product to be deleted. Must not be null.
     * @return A {@code DeleteItemResponse} object indicating the success or failure of the deletion.
     */
    DeleteItemResponse deleteProductById(@NotNull UUID productId);
}

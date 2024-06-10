package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.repository.ProductRepository;
import com.mikejacks.international_currency_converter.landedcost.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for managing products.
 *
 * <p>This class provides methods for querying, adding, updating, and deleting products
 * in the application. It utilizes a {@code ProductRepository} for database operations.</p>
 */
@Service
public class BaseProductService implements ProductService {
    private final ProductRepository productRepository;

    /**
     * Constructs a new {@code BaseProductService} with the specified {@code ProductRepository}.
     *
     * @param productRepository The repository used to handle product data operations.
     */
    @Autowired
    public BaseProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Query Services

    /**
     * Retrieves a list of all products.
     *
     * <p>This method fetches all {@code Product} objects from the repository and returns them as a list.</p>
     *
     * @return A list of all {@code Product} objects.
     */
    @Override public List<Product> products() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product based on the specified product ID or name.
     *
     * <p>This method fetches a {@code Product} object from the repository based on the provided product ID or name.
     * If both the product ID and name are provided, an {@code IllegalArgumentException} is thrown. If neither is
     * provided, another {@code IllegalArgumentException} is thrown.</p>
     *
     * @param productId The UUID of the product to retrieve. Can be null.
     * @param name The name of the product to retrieve. Can be null.
     * @return The {@code Product} object matching the specified product ID or name.
     * @throws IllegalArgumentException if both or neither the product ID and name are provided.
     */
    @Override public Product product(UUID productId, String name) {
        if (productId != null && name != null) {
            throw new IllegalArgumentException("Only provide a product id or a product name, not both.");
        } else if (productId != null) {
            return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product id " + productId + " not found."));
        } else if (name != null) {
            return productRepository.findProductByName(name).orElseThrow(() -> new IllegalArgumentException("Product name " + name + " not found."));
        } else {
            throw new IllegalArgumentException("Please provide either a product id or a product name.");
        }
    }

    /**
     * Retrieves a product based on the specified product ID.
     *
     * <p>This method fetches a {@code Product} object from the repository based on the provided product ID.
     * If the product is not found, {@code null} is returned.</p>
     *
     * @param id The UUID of the product to retrieve.
     * @return The {@code Product} object matching the specified product ID, or {@code null} if no such product is found.
     */
    @Override public Product productById(UUID id) {
        return productRepository.findProductById(id).orElse(null);
    }

    /**
     * Retrieves a product based on the specified product name.
     *
     * <p>This method fetches a {@code Product} object from the repository based on the provided product name.
     * If the product is not found, {@code null} is returned.</p>
     *
     * @param name The name of the product to retrieve. Must not be null.
     * @return The {@code Product} object matching the specified product name, or {@code null} if no such product is found.
     */
    @Override public Product productByName(@NotNull String name) {
        return productRepository.findProductByName(name).orElse(null);
    }

    /**
     * Retrieves a list of products with prices less than or equal to the specified maximum price.
     *
     * <p>This method fetches {@code Product} objects from the repository with prices less than or equal to the specified
     * maximum price and returns them as a list.</p>
     *
     * @param maxPrice The maximum price of the products to retrieve. Must not be null.
     * @return A list of {@code Product} objects with prices less than or equal to the specified maximum price.
     */
    @Override public List<Product> productsByPriceLessThanOrEqualTo(@NotNull Double maxPrice) {
        return productRepository.findProductsByPriceLessThanEqual(maxPrice);
    }

    /**
     * Retrieves a list of products with prices greater than or equal to the specified minimum price.
     *
     * <p>This method fetches {@code Product} objects from the repository with prices greater than or equal to the specified
     * minimum price and returns them as a list.</p>
     *
     * @param minPrice The minimum price of the products to retrieve. Must not be null.
     * @return A list of {@code Product} objects with prices greater than or equal to the specified minimum price.
     */
    @Override public List<Product> productsByPriceGreaterThanOrEqualTo(@NotNull Double minPrice) {
        return productRepository.findProductsByPriceGreaterThanEqual(minPrice);
    }

    /**
     * Retrieves a list of products with prices between the specified minimum and maximum prices.
     *
     * <p>This method fetches {@code Product} objects from the repository with prices between the specified minimum and
     * maximum prices and returns them as a list.</p>
     *
     * @param minPrice The minimum price of the products to retrieve. Must not be null.
     * @param maxPrice The maximum price of the products to retrieve. Must not be null.
     * @return A list of {@code Product} objects with prices between the specified minimum and maximum prices.
     */
    @Override public List<Product> productsByPriceBetween(@NotNull Double minPrice, @NotNull Double maxPrice) {
        return productRepository.findProductsByPriceBetween(minPrice, maxPrice);
    }

    // Mutation Services

    /**
     * Adds a new product to the database.
     *
     * <p>This method creates a new {@code Product} object using the details provided in the {@code ProductCreateInput} object.
     * The new product is then saved to the database.</p>
     *
     * @param productCreateInput A {@code ProductCreateInput} object containing the details of the new product to be added.
     * @return The newly added {@code Product} object.
     */
    @Override public Product addProduct(@NotNull ProductCreateInput productCreateInput) {
        Product newProduct = new Product(productCreateInput.getName(), productCreateInput.getPrice(), productCreateInput.getCurrencyCode());
        return productRepository.save(newProduct);
    }

    /**
     * Updates an existing product's details by its ID or name.
     *
     * <p>This method finds an existing {@code Product} object by its ID or name. If the product exists, it updates its details
     * with the information provided in the {@code ProductUpdateInput} object. If the product does not exist, an {@code IllegalArgumentException}
     * is thrown. The updated product is then saved to the database and returned.</p>
     *
     * @param productId The UUID of the product to be updated. Can be null if name is provided.
     * @param name The name of the product to be updated. Can be null if productId is provided.
     * @param productUpdateInput A {@code ProductUpdateInput} object containing the updated details of the product.
     * @return The updated {@code Product} object.
     * @throws IllegalArgumentException if the product does not exist or if both productId and name are provided.
     */
    @Override public Product updateProduct(UUID productId, String name, @NotNull ProductUpdateInput productUpdateInput) {
        Product existingProduct;
        if (productId != null && name == null) {
            existingProduct = productRepository.findProductById(productId).orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found."));
        } else if (productId == null && name != null) {
            existingProduct = productRepository.findProductByName(name).orElseThrow(() -> new RuntimeException("Product with name " + name + " not found."));
        } else if (productId == null && name == null) {
            throw new IllegalArgumentException("No arguments present. You must have only one of the following arguments: productId, name, or code");
        } else {
            throw new IllegalArgumentException("Too many arguments present. You must have only one of the following arguments: productId, name, or code");
        }
        if (productUpdateInput.getName() != null) {
            existingProduct.setName(productUpdateInput.getName());
        }
        if (productUpdateInput.getPrice() != null) {
            existingProduct.setPrice(productUpdateInput.getPrice());
        }
        return productRepository.save(existingProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * <p>This method attempts to find a {@code Product} object by its ID. If the product is found, it is deleted from the
     * repository. If the product is not found, a {@code DeleteItemResponse} indicating failure is returned.</p>
     *
     * @param productId The UUID of the product to be deleted. Must not be null.
     * @return A {@code DeleteItemResponse} indicating the success or failure of the deletion operation.
     *         If the product is successfully deleted, the response contains the product ID and a success message.
     *         If the product is not found, the response contains a failure message and a null product ID.
     */
    @Override public DeleteItemResponse deleteProductById(final @NotNull UUID productId) {
       Optional<Product> productOptional = productRepository.findProductById(productId);
       if (productOptional.isEmpty()) {
          return new DeleteItemResponse(false,  String.format("unable to find product with the id %s", productId), null);
       }
       Product existingProduct = productOptional.get();
       productRepository.delete(existingProduct);
       return new DeleteItemResponse(true, String.format("product %s successfully deleted", existingProduct.getName()),productId);
    }
}

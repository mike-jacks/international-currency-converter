package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.service.ProductService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * Data fetcher for product queries in the GraphQL API.
 *
 * <p>This class handles the GraphQL queries related to products, utilizing the {@code ProductService}
 * to retrieve product data.</p>
 */
@DgsComponent
public class ProductQueryDataFetcher {
    private final ProductService productService;

    /**
     * Constructs a new {@code ProductQueryDataFetcher} with the specified {@code ProductService}.
     *
     * @param productService The service used to handle product data operations.
     */
    @Autowired
    public ProductQueryDataFetcher(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves a list of all products.
     *
     * @return A list of all {@code Product} objects.
     */
    @DgsQuery
    public List<Product> products() {
        return productService.products();
    }

    /**
     * Retrieves a product based on the specified product ID or name.
     *
     * <p>If both productId and name are provided, an {@code IllegalArgumentException} is thrown.
     * If only one of productId or name is provided, it returns the corresponding product.
     * If neither productId nor name is provided, an {@code IllegalArgumentException} is thrown.</p>
     *
     * @param productId The unique ID of the product to retrieve. Can be null.
     * @param name The name of the product to retrieve. Can be null.
     * @return The {@code Product} object matching the specified criteria.
     */
    @DgsQuery
    public Product product(@InputArgument UUID productId, String name) {
        return productService.product(productId, name);
    }

    /**
     * Retrieves a product based on the specified product ID.
     *
     * @param productId The unique ID of the product to retrieve. Must not be null.
     * @return The {@code Product} object matching the specified ID.
     */
    @DgsQuery
    public Product productById(@InputArgument UUID productId) {
       return productService.productById(productId);
    }

    /**
     * Retrieves a product based on the specified name.
     *
     * @param name The name of the product to retrieve. Must not be null.
     * @return The {@code Product} object matching the specified name.
     */
    @DgsQuery
    public Product productByName(@InputArgument String name) {
        return productService.productByName(name);
    }

    /**
     * Retrieves a list of products with a price less than or equal to the specified maximum price.
     *
     * @param maxPrice The maximum price to filter products by. Must not be null.
     * @return A list of {@code Product} objects with a price less than or equal to the specified maximum price.
     */
    @DgsQuery
    public List<Product> productsByPriceLessThanOrEqualTo(@InputArgument Double maxPrice) {
        return productService.productsByPriceLessThanOrEqualTo(maxPrice);
    }

    /**
     * Retrieves a list of products with a price greater than or equal to the specified minimum price.
     *
     * @param minPrice The minimum price to filter products by. Must not be null.
     * @return A list of {@code Product} objects with a price greater than or equal to the specified minimum price.
     */
    @DgsQuery
    public List<Product> productsByPriceGreaterThanOrEqualTo(@InputArgument Double minPrice) {
        return productService.productsByPriceGreaterThanOrEqualTo(minPrice);
    }

    /**
     * Retrieves a list of products with a price between the specified minimum and maximum prices.
     *
     * @param minPrice The minimum price to filter products by. Must not be null.
     * @param maxPrice The maximum price to filter products by. Must not be null.
     * @return A list of {@code Product} objects with a price between the specified minimum and maximum prices.
     */
    @DgsQuery
    public List<Product> productsByPriceBetween(@InputArgument Double minPrice, @InputArgument Double maxPrice) {
        return productService.productsByPriceBetween(minPrice, maxPrice);
    }
}

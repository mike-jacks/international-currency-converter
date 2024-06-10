package com.mikejacks.international_currency_converter.landedcost.datafetcher.mutation;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.service.ProductService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Data fetcher for product mutations in the GraphQL API.
 *
 * <p>This class handles the GraphQL mutations related to products, utilizing the {@code ProductService}
 * to add, update, and delete product data.</p>
 */
@DgsComponent
public class ProductMutationDateFetcher {

    private final ProductService productService;

    /**
     * Constructs a new {@code ProductMutationDateFetcher} with the specified {@code ProductService}.
     *
     * @param productService The service used to handle product data operations.
     */
    @Autowired
    public ProductMutationDateFetcher(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Adds a new product based on the provided input data.
     *
     * @param product The {@code ProductCreateInput} object containing the details of the product to add.
     * @return The newly created {@code Product} object.
     */
    @DgsMutation
    public Product addProduct(@InputArgument ProductCreateInput product) {
        return productService.addProduct(product);
    }

    /**
     * Updates an existing product identified by the specified ID or name with the provided input data.
     *
     * @param productId The unique ID of the product to update. Can be null.
     * @param name The name of the product to update. Can be null.
     * @param product The {@code ProductUpdateInput} object containing the updated details of the product.
     * @return The updated {@code Product} object.
     * @throws IllegalArgumentException if both productId and name are provided, or if neither are provided.
     */
    @DgsMutation
    public Product updateProduct(@InputArgument UUID productId, @InputArgument String name, @InputArgument ProductUpdateInput product) {
        return productService.updateProduct(productId, name, product);
    }

    /**
     * Updates an existing product identified by the specified ID with the provided input data.
     *
     * @param productId The unique ID of the product to update. Must not be null.
     * @param product The {@code ProductUpdateInput} object containing the updated details of the product.
     * @return The updated {@code Product} object.
     * @throws IllegalArgumentException if productId is null.
     */
    @DgsMutation
    public Product updateProductById(@InputArgument UUID productId, @InputArgument ProductUpdateInput product) {
        return productService.updateProduct(productId, null, product);
    }

    /**
     * Updates an existing product identified by the specified name with the provided input data.
     *
     * @param name The name of the product to update. Must not be null.
     * @param product The {@code ProductUpdateInput} object containing the updated details of the product.
     * @return The updated {@code Product} object.
     * @throws IllegalArgumentException if name is null.
     */
    @DgsMutation
    public Product updateProductByName(@InputArgument String name, @InputArgument ProductUpdateInput product) {
        return productService.updateProduct(null, name, product);
    }

    /**
     * Deletes an existing product identified by the specified ID.
     *
     * @param productId The unique ID of the product to delete. Must not be null.
     * @return A {@code DeleteItemResponse} indicating the success or failure of the deletion operation.
     * @throws IllegalArgumentException if productId is null.
     */
    @DgsMutation
    public DeleteItemResponse deleteProductById(@InputArgument UUID productId) {
        return productService.deleteProductById(productId);
    }
}

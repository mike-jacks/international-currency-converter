package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Mock implementation of the ProductService interface.
 * This service provides methods for managing products including adding, updating, retrieving, and deleting products.
 */
@Service
public class MockProductService  implements ProductService {
    public List<Product> products;
    public ArrayList<Product> mutableProducts;


    /**
     * Constructor for MockProductService.
     * Initializes the service with a list of products and creates a mutable copy of the product list.
     *
     * @param products List of initial products
     */
    public MockProductService(List<Product> products) {
        this.products = products;
        this.mutableProducts = new ArrayList<>();
        for (Product product : products) {
            this.mutableProducts.add(new Product(product.getId(), product.getName(), product.getPrice(), product.getCurrencyCode()));
        }
    }

    /**
     * Retrieves the list of all products.
     *
     * @return List of Products
     */
    @Override
    public List<Product> products() {
        return products;
    }

    /**
     * Retrieves a product by its ID or name.
     *
     * <p>Note: Only one of the parameters should be provided. If both are provided,
     * an InvalidParameterException will be thrown. If neither is provided, the same
     * exception will be thrown.</p>
     *
     * @param productId the ID of the product (can be null if name is provided)
     * @param name      the name of the product (can be null if productId is provided)
     * @return a product object if found, else null
     * @throws InvalidParameterException if both productId and name are provided, or if neither is provided
     */
    @Override
    public Product product(UUID productId, String name) {
        if (productId != null && name != null) {
            throw new InvalidParameterException("Only provide a product id or a product name, not both.");
        } else if (productId != null) {
            for (Product product : products) {
                if (productId.equals(product.getId())) {
                    return product;
                }
            }
            return null;
        } else if (name != null) {
            for (Product product : products) {
                if (name.equals(product.getName())) {
                    return product;
                }
            }
            return null;
        } else {
            throw new InvalidParameterException("Please provide either a product id or a product name.");
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return a product object if found, else null
     */
    @Override
    public Product productById(@NotNull UUID id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Retrieves a product by its name.
     *
     * @param name the name of the product
     * @return a product object if found, else null
     */
    @Override
    public Product productByName(@NotNull String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of products with prices less than or equal to the specified maximum price.
     *
     * @param maxPrice the maximum price
     * @return a list of products with prices less than or equal to the max price
     */
    @Override
    public List<Product> productsByPriceLessThanOrEqualTo(@NotNull Double maxPrice) {
        return products.stream().filter(product -> product.getPrice() <= maxPrice).toList();
    }

    /**
     * Retrieves a list of products with prices greater than or equal to the specified minimum price.
     *
     * @param minPrice the minimum price
     * @return a list of products with prices greater than or equal to the min price
     */
    @Override
    public List<Product> productsByPriceGreaterThanOrEqualTo(@NotNull Double minPrice) {
        return products.stream().filter(product -> product.getPrice() >= minPrice).toList();
    }

    /**
     * Retrieves a list of products with prices between the specified minimum and maximum prices.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return List of products
     */
    @Override
    public List<Product> productsByPriceBetween(@NotNull Double minPrice, @NotNull Double maxPrice) {
        return products.stream().filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice).toList();
    }

    /**
     * Adds a new product based on the provided input.
     *
     * @param productCreateInput the input data for creating the product
     * @return the created product
     */
    @Override
    public Product addProduct(@NotNull ProductCreateInput productCreateInput) {
        Product product = new Product(productCreateInput.getName(), productCreateInput.getPrice(), productCreateInput.getCurrencyCode());
        mutableProducts.add(product);
        return product;
    }

    /**
     * Updates an existing product identified by its ID or name.
     *
     * @param productId           the ID of the product to update (can be null if name is provided)
     * @param name                the name of the product to update (can be null if productId is provided)
     * @param productUpdateInput  the input data for updating the product
     * @return the updated product
     * @throws IllegalArgumentException if both productId and name are provided, or if neither is provided
     */
    @Override
    public Product updateProduct(UUID productId, String name, @NotNull ProductUpdateInput productUpdateInput) {
        Product existingProduct;
        if (productId != null && name != null) {
            throw new IllegalArgumentException("Too many arguments present. You must have only one of the following arguments: productId, name, or code");
        } else if (productId != null) {
            existingProduct = mutableProducts.stream().filter(product -> product.getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found."));
        } else if (name != null) {
            existingProduct= mutableProducts.stream().filter(product -> product.getName().equals(name)).findFirst().orElseThrow(() -> new RuntimeException("Product with name " + name + " not found."));
        } else {
            throw new IllegalArgumentException("No arguments present. You must have only one of the following arguments: productId, name, or code");
        }
        if (productUpdateInput.getName() != null) {
            existingProduct.setName(productUpdateInput.getName());
        }
        if (productUpdateInput.getPrice() != null) {
            existingProduct.setPrice(productUpdateInput.getPrice());
        }
        return existingProduct;
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to delete
     * @return a response indicating the result of the deletion
     */
    @Override
    public DeleteItemResponse deleteProductById(@NotNull UUID productId) {
        Optional<Product> existingProductOptional = mutableProducts.stream().filter(product -> product.getId().equals(productId)).findFirst();
        if (existingProductOptional.isEmpty()) {
            return new DeleteItemResponse(false, String.format("unable to find country with the id %s", productId), null);
        }
        Product existingProduct =  existingProductOptional.get();
        mutableProducts.remove(existingProduct);
        return new DeleteItemResponse(true, String.format("'%s' has been successfully deleted.", existingProduct.getName()), productId);
    }
}

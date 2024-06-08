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

@Service
public class BaseProductService implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public BaseProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Query Services
    @Override public List<Product> products() {
        return productRepository.findAll();
    }

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

    @Override public Product productById(UUID id) {
        return productRepository.findProductById(id).orElse(null);
    }

    @Override public Product productByName(@NotNull String name) {
        return productRepository.findProductByName(name).orElse(null);
    }

    @Override public List<Product> productsByPriceLessThanOrEqualTo(@NotNull Double maxPrice) {
        return productRepository.findProductsByPriceLessThanEqual(maxPrice);
    }

    @Override public List<Product> productsByPriceGreaterThanOrEqualTo(@NotNull Double minPrice) {
        return productRepository.findProductsByPriceGreaterThanEqual(minPrice);
    }

    @Override public List<Product> productsByPriceBetween(@NotNull Double minPrice, @NotNull Double maxPrice) {
        return productRepository.findProductsByPriceBetween(minPrice, maxPrice);
    }

    // Mutation Services

    @Override public Product addProduct(@NotNull ProductCreateInput productCreateInput) {
        Product newProduct = new Product(productCreateInput.getName(), productCreateInput.getPrice(), productCreateInput.getCurrencyCode());
        return productRepository.save(newProduct);
    }

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

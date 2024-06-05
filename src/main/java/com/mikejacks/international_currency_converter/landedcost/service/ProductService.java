package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Query Services
    public List<Product> products() {
        return productRepository.findAll();
    }

    public Product productById(UUID id) {
        return productRepository.findProductById(id).orElse(null);
    }

    public Product productByName(String name) {
        return productRepository.findProductByName(name).orElse(null);
    }

    public List<Product> productsByPriceLessThanOrEqualTo(Double maxPrice) {
        return productRepository.findProductsByPriceLessThanEqual(maxPrice);
    }

    public List<Product> productsByPriceGreaterThanOrEqualTo(Double minPrice) {
        return productRepository.findProductsByPriceGreaterThanEqual(minPrice);
    }

    public List<Product> productsByPriceBetween(Double minPrice, Double maxPrice) {
        return productRepository.findProductsByPriceBetween(minPrice, maxPrice);
    }

    // Mutation Services

    public Product addProduct(@NotNull ProductCreateInput productCreateInput) {
        Product newProduct = new Product(productCreateInput.getName(), productCreateInput.getPrice(), productCreateInput.getCurrencyCode());
        return productRepository.save(newProduct);
    }

    public Product updateProduct(UUID id, String name, @NotNull ProductUpdateInput productUpdateInput) {
        Product existingProduct;
        if (id != null) {
           existingProduct = productRepository.findProductById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        } else if (name != null) {
           existingProduct = productRepository.findProductByName(name).orElseThrow(() -> new RuntimeException("Product not found"));
        } else {
            throw new RuntimeException("Either id or name must be provided");
        }
        if (productUpdateInput.getName() != null) {
            existingProduct.setName(productUpdateInput.getName());
        }
        if (productUpdateInput.getPrice() != null) {
            existingProduct.setPrice(productUpdateInput.getPrice());
        }
        return productRepository.save(existingProduct);
    }

    public DeleteItemResponse deleteProductById(@NotNull UUID productId) {
       Optional<Product> productOptional = productRepository.findProductById(productId);
       if (productOptional.isEmpty()) {
          return new DeleteItemResponse(false,  String.format("unable to find product with the id %s", productId), null);
       }
       Product existingProduct = productOptional.get();
       productRepository.delete(existingProduct);
       return new DeleteItemResponse(true, String.format("product %s successfully deleted", existingProduct.getName()),productId);
    }


}

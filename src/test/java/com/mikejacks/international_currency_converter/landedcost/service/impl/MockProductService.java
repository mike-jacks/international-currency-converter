package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
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

@Service
public class MockProductService  implements ProductService {
    public List<Product> products;
    public ArrayList<Product> mutableProducts;


    public MockProductService(List<Product> products) {
        this.products = products;
        this.mutableProducts = new ArrayList<>();
        for (Product product : products) {
            this.mutableProducts.add(new Product(product.getId(), product.getName(), product.getPrice(), product.getCurrencyCode()));
        }
    }

    /**
     * @return List of Products
     */
    @Override
    public List<Product> products() {
        return products;
    }

     /**
     * Retrieves a product by its id or name.
     *
     * <p>Note: Only one of the parameters should be provided. If both are provided,
     * an InvalidParameterException will be thrown. If neither is provided, the same
     * exception will be thrown.</p>
     *
     * @param productId the ID of the product (can be null if name is provided)
     * @param name the name of the product (can be null if productId is provided)
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
     * @param id
     * @return
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
     * @param name
     * @return
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
     * @param maxPrice
     * @return
     */
    @Override
    public List<Product> productsByPriceLessThanOrEqualTo(@NotNull Double maxPrice) {
        return products.stream().filter(product -> product.getPrice() <= maxPrice).toList();
    }

    /**
     * @param minPrice
     * @return
     */
    @Override
    public List<Product> productsByPriceGreaterThanOrEqualTo(@NotNull Double minPrice) {
        return products.stream().filter(product -> product.getPrice() >= minPrice).toList();
    }

    /**
     * @param minPrice
     * @param maxPrice
     * @return List of products
     */
    @Override
    public List<Product> productsByPriceBetween(@NotNull Double minPrice, @NotNull Double maxPrice) {
        return products.stream().filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice).toList();
    }

    /**
     * @param productCreateInput
     * @return
     */
    @Override
    public Product addProduct(@NotNull ProductCreateInput productCreateInput) {
        Product product = new Product(productCreateInput.getName(), productCreateInput.getPrice(), productCreateInput.getCurrencyCode());
        mutableProducts.add(product);
        return product;
    }

    /**
     * @param productId
     * @param name
     * @param productUpdateInput
     * @return
     */
    @Override
    public Product updateProduct(UUID productId, String name, @NotNull ProductUpdateInput productUpdateInput) {
        Product existingProduct;
        if (productId != null && name == null) {
            existingProduct = mutableProducts.stream().filter(product -> product.getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found."));
        } else if (productId == null && name != null) {
            existingProduct= mutableProducts.stream().filter(product -> product.getName().equals(name)).findFirst().orElseThrow(() -> new RuntimeException("Product with name " + name + " not found."));
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
        return existingProduct;
    }

    /**
     * @param productId
     * @return
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

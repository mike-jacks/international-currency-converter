package com.mikejacks.international_currency_converter.landedcost.service.impl;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@Service
public class MockProductService  implements ProductService {
    public List<Product> products;

    public MockProductService(List<Product> products) {
        this.products = products;
    }


    /**
     * @return
     */
    @Override
    public List<Product> products() {
        return products;
    }

    /**
     *
     * @param productId
     * @param name
     * @return
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
     * @return
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
        return null;
    }

    /**
     * @param id
     * @param name
     * @param productUpdateInput
     * @return
     */
    @Override
    public Product updateProduct(UUID id, String name, @NotNull ProductUpdateInput productUpdateInput) {
        return null;
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public DeleteItemResponse deleteProductById(@NotNull UUID productId) {
        return null;
    }
}

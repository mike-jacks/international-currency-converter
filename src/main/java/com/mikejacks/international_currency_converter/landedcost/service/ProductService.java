package com.mikejacks.international_currency_converter.landedcost.service;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    // Query Services
    List<Product> products();

    Product product(UUID id, String name);

    Product productById(@NotNull UUID id);

    Product productByName(@NotNull String name);

    List<Product> productsByPriceLessThanOrEqualTo(@NotNull Double maxPrice);

    List<Product> productsByPriceGreaterThanOrEqualTo(@NotNull Double minPrice);

    List<Product> productsByPriceBetween(@NotNull Double minPrice, @NotNull Double maxPrice);

    Product addProduct(@NotNull ProductCreateInput productCreateInput);

    Product updateProduct(UUID id, String name, @NotNull ProductUpdateInput productUpdateInput);

    DeleteItemResponse deleteProductById(@NotNull UUID productId);
}

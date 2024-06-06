package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.service.ProductService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class ProductQueryDataFetcher {
    private final ProductService productService;

    @Autowired
    public ProductQueryDataFetcher(ProductService productService) {
        this.productService = productService;
    }

    @DgsQuery
    public List<Product> products() {
        return productService.products();
    }

    @DgsQuery
    public Product product(@InputArgument UUID productId, String name) {
        return productService.product(productId, name);
    }

    @DgsQuery
    public Product productById(@InputArgument UUID productId) {
       return productService.productById(productId);
    }

    @DgsQuery
    public Product productByName(@InputArgument String name) {
        return productService.productByName(name);
    }

    @DgsQuery
    public List<Product> productsByPriceLessThanOrEqualTo(@InputArgument Double maxPrice) {
        return productService.productsByPriceLessThanOrEqualTo(maxPrice);
    }

    @DgsQuery
    public List<Product> productsByPriceGreaterThanOrEqualTo(@InputArgument Double minPrice) {
        return productService.productsByPriceGreaterThanOrEqualTo(minPrice);
    }

    @DgsQuery
    public List<Product> productsByPriceBetween(@InputArgument Double minPrice, @InputArgument Double maxPrice) {
        return productService.productsByPriceBetween(minPrice, maxPrice);
    }





}

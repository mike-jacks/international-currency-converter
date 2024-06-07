package com.mikejacks.international_currency_converter.landedcost.datafetcher.mutation;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.service.impl.MockProductService;
import com.netflix.graphql.dgs.DgsComponent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DgsComponent
class ProductMutationDateFetcherTest {

    private static @NotNull Stream<Arguments> mockProductsLists() {
        return Stream.of(
                Arguments.of(Collections.emptyList(), "Empty list"),
                Arguments.of(Collections.singletonList(new Product("Thermal Detonator", 100.00, "HOT")), "One item"),
                Arguments.of(Arrays.asList(
                        new Product("LightSaber", 1000.00, "TTO"),
                        new Product("ThermalDetonator", 100.00, "HOT"),
                        new Product("Blaster", 300.00, "TTO"),
                        new Product("X-Wing", 49999.99, "NAB"),
                        new Product("Aluminum Falcon", 99999.99, "NAB")), "Many items")
        );
    }


    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void addProduct(List<Product> expectedProducts, String testName) {
        Integer productsBeginningListSize = expectedProducts.size();
        MockProductService productService = new MockProductService(expectedProducts);
        ProductMutationDateFetcher productMutationDataFetcher = new ProductMutationDateFetcher(productService);
        ProductCreateInput newProduct = new ProductCreateInput("Blaster", 550.00, "TTO");
        Product addedProduct = productMutationDataFetcher.addProduct(newProduct);
        assertEquals(newProduct.getName(), addedProduct.getName());
        assertEquals(newProduct.getPrice(), addedProduct.getPrice());
        assertEquals(newProduct.getCurrencyCode(), addedProduct.getCurrencyCode());
        assertEquals(productsBeginningListSize + 1, productService.mutableProducts.size());

    }

    @Test
    void updateProductById() {
    }

    @Test
    void updateProductByName() {
    }

    @Test
    void deleteProductById() {
    }
}
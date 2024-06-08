package com.mikejacks.international_currency_converter.landedcost.datafetcher.mutation;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.service.impl.MockProductService;
import com.netflix.graphql.dgs.DgsComponent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

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
    void testAddProduct(@NotNull List<Product> expectedProducts, String testName) {
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
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void testUpdateProduct(@NotNull List<Product> expectedProducts, String testName) {
        Integer productsBeginningListSize = expectedProducts.size();
        MockProductService productService = new MockProductService(expectedProducts);
        ProductMutationDateFetcher productMutationDataFetcher = new ProductMutationDateFetcher(productService);
        ProductUpdateInput newProduct = new ProductUpdateInput("Storm Trooper Rifle", 550.00, "IMP");
        if (productsBeginningListSize == 0) {
            UUID randomUUID = UUID.randomUUID();
            String name = "Lightsaber";
            RuntimeException uuidException = assertThrows(RuntimeException.class, () -> productMutationDataFetcher.updateProduct(randomUUID, null, newProduct));
            RuntimeException nameException = assertThrows(RuntimeException.class, () -> productMutationDataFetcher.updateProduct(null, name, newProduct));
            assertEquals("Product with id " + randomUUID + " not found.", uuidException.getMessage());
            assertEquals("Product with name " + name + " not found.", nameException.getMessage());
            assertEquals(productsBeginningListSize, productService.mutableProducts.size());
        } else {
            Product existingProduct = expectedProducts.get(productsBeginningListSize - 1);

            Product updatedProductUsingIdArgument = productMutationDataFetcher.updateProduct(existingProduct.getId(), null, newProduct);
            assertNotEquals(existingProduct.getName(), updatedProductUsingIdArgument.getName());
            assertEquals(productService.mutableProducts.get(productsBeginningListSize - 1), updatedProductUsingIdArgument);

            Product resetProductUsingIdArgument = productMutationDataFetcher.updateProduct(existingProduct.getId(), null, new ProductUpdateInput(existingProduct.getName(), existingProduct.getPrice(), existingProduct.getCurrencyCode()));
            assertEquals(existingProduct.getName(), resetProductUsingIdArgument.getName());

            Product updatedProductUsingNameArgument = productMutationDataFetcher.updateProduct(null, existingProduct.getName(), newProduct);
            assertNotEquals(existingProduct, updatedProductUsingNameArgument);
            assertEquals(productService.mutableProducts.get(productsBeginningListSize - 1), updatedProductUsingNameArgument);

            IllegalArgumentException updateProductUsingNullArguments = assertThrows(IllegalArgumentException.class, () -> productMutationDataFetcher.updateProduct(null, null, newProduct));
            IllegalArgumentException updateProductsUsingBothArguments = assertThrows(IllegalArgumentException.class, () -> productMutationDataFetcher.updateProduct(existingProduct.getId(), existingProduct.getName(), newProduct));

            assertNotEquals(productService.products.get(productsBeginningListSize-1), productService.mutableProducts.get(productsBeginningListSize-1));
            assertEquals("No arguments present. You must have only one of the following arguments: productId, name, or code", updateProductUsingNullArguments.getMessage());
            assertEquals("Too many arguments present. You must have only one of the following arguments: productId, name, or code", updateProductsUsingBothArguments.getMessage());
            assertEquals(productsBeginningListSize, productService.mutableProducts.size());
        }
    }

    @ParameterizedTest(name= "{1}")
    @MethodSource("mockProductsLists")
    void testUpdateProductById(@NotNull List<Product> expectedProducts, String testName) {
        Integer productsBeginningListSize = expectedProducts.size();
        MockProductService productService = new MockProductService(expectedProducts);
        ProductMutationDateFetcher productMutationDataFetcher = new ProductMutationDateFetcher(productService);
        ProductUpdateInput newProduct = new ProductUpdateInput("Blaster", 550.00, "TTO");
        if (productsBeginningListSize == 0) {
            UUID randomUUID = UUID.randomUUID();
            RuntimeException updatedProduct = assertThrows(RuntimeException.class, () -> productMutationDataFetcher.updateProductById(randomUUID, newProduct));
            assertEquals("Product with id " + randomUUID + " not found.", updatedProduct.getMessage());
        } else {
            Product existingProduct = expectedProducts.get(productsBeginningListSize - 1);
            Product updatedProduct = productMutationDataFetcher.updateProductById(existingProduct.getId(), newProduct);
            assertNotEquals(existingProduct.getName(), updatedProduct.getName());
            assertEquals(newProduct.getName(), updatedProduct.getName());
            assertEquals(productService.mutableProducts.get(productsBeginningListSize - 1), updatedProduct);
        }

    }

    @ParameterizedTest(name= "{1}")
    @MethodSource("mockProductsLists")
    void testUpdateProductByName(@NotNull List<Product> expectedProducts, String testName) {
        Integer productsBeginningListSize = expectedProducts.size();
        MockProductService productService = new MockProductService(expectedProducts);
        ProductMutationDateFetcher productMutationDataFetcher = new ProductMutationDateFetcher(productService);
        ProductUpdateInput newProduct = new ProductUpdateInput("Blaster", 550.00, "TTO");
        if (productsBeginningListSize == 0) {
            String name = "X-Wing";
            RuntimeException updatedProduct = assertThrows(RuntimeException.class, () -> productMutationDataFetcher.updateProductByName(name, newProduct));
            assertEquals("Product with name " + name + " not found.", updatedProduct.getMessage());
        } else {
            Product existingProduct = expectedProducts.get(productsBeginningListSize - 1);
            Product updatedProduct = productMutationDataFetcher.updateProductByName(existingProduct.getName(), newProduct);
            assertNotEquals(existingProduct.getName(), updatedProduct.getName());
            assertEquals(newProduct.getName(), updatedProduct.getName());
            assertEquals(productService.mutableProducts.get(productsBeginningListSize - 1), updatedProduct);
        }

    }


    @ParameterizedTest(name= "{1}")
    @MethodSource("mockProductsLists")
    void testDeleteProductById(@NotNull List<Product> expectedProducts, String testName) {
        Integer productsBeginningListSize = expectedProducts.size();
        MockProductService productService = new MockProductService(expectedProducts);
        ProductMutationDateFetcher productMutationDataFetcher = new ProductMutationDateFetcher(productService);
        if (productsBeginningListSize == 0) {
            UUID randomUUID = UUID.randomUUID();
            DeleteItemResponse deleteItemResponse = productMutationDataFetcher.deleteProductById(randomUUID);
            assertEquals(false, deleteItemResponse.success());
            assertEquals(String.format("unable to find country with the id %s", randomUUID), deleteItemResponse.message());
            assertNull(deleteItemResponse.deletedItemId());
        } else {
            Product existingProduct = expectedProducts.get(productsBeginningListSize - 1);
            DeleteItemResponse deleteItemResponse = productMutationDataFetcher.deleteProductById(existingProduct.getId());
            assertEquals(true, deleteItemResponse.success());
            assertEquals(String.format("'%s' has been successfully deleted.", existingProduct.getName()), deleteItemResponse.message());
            assertEquals(existingProduct.getId(), deleteItemResponse.deletedItemId());
        }

    }
}
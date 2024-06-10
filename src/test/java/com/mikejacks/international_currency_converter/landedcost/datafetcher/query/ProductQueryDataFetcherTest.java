package com.mikejacks.international_currency_converter.landedcost.datafetcher.query;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.service.impl.MockProductService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@code ProductQueryDataFetcher}.
 *
 * <p>This class tests the query methods of the {@code ProductQueryDataFetcher} class using mock data.</p>
 */
@ExtendWith(MockitoExtension.class)
public class ProductQueryDataFetcherTest {

    /**
     * Provides mock product lists for parameterized tests.
     *
     * @return A stream of arguments containing mock product lists and test names.
     */
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

    /**
     * Tests the {@code products} method of {@code ProductQueryDataFetcher}.
     *
     * @param expectedProducts The expected list of products.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void testProducts(List<Product> expectedProducts, String testName) {
        MockProductService productService = new MockProductService(expectedProducts);
        ProductQueryDataFetcher productQueryDataFetcher = new ProductQueryDataFetcher(productService);
        List<Product> results = productQueryDataFetcher.products();
        assertEquals(expectedProducts.size(), results.size());
    }

    /**
     * Tests the {@code product} method of {@code ProductQueryDataFetcher}.
     *
     * @param expectedProducts The expected list of products.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void testProduct(List<Product> expectedProducts, String testName) {
        MockProductService productService = new MockProductService(expectedProducts);
        ProductQueryDataFetcher productQueryDataFetcher = new ProductQueryDataFetcher(productService);

        UUID id = !expectedProducts.isEmpty() ? expectedProducts.get(0).getId() : null;
        String name = !expectedProducts.isEmpty() ? expectedProducts.get(0).getName() : null;

        if (id != null && name != null) {
            InvalidParameterException exception = assertThrows(
                    InvalidParameterException.class, () -> productQueryDataFetcher.product(id, name)
            );
            assertEquals("Only provide a product id or a product name, not both.", exception.getMessage());
        } else if (id == null && name == null) {
            InvalidParameterException exception = assertThrows(
                    InvalidParameterException.class, () -> productQueryDataFetcher.product(null, null)
            );
            assertEquals("Please provide either a product id or a product name.", exception.getMessage());
        } else {
            Product resultProductByName;
            Product resultProductById;

            if (id != null) {
                resultProductById = productQueryDataFetcher.product(id, null);
                assertEquals(id, resultProductById.getId());
            }

            if (name != null) {
                resultProductByName = productQueryDataFetcher.product(null, name);
                assertEquals(name, resultProductByName.getName());
            }
        }
    }

    /**
     * Tests the {@code productById} method of {@code ProductQueryDataFetcher}.
     *
     * @param expectedProducts The expected list of products.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void testProductById(List<Product> expectedProducts, String testName) {
        MockProductService productService = new MockProductService(expectedProducts);
        ProductQueryDataFetcher productQueryDataFetcher = new ProductQueryDataFetcher(productService);
        UUID expectedProductId = !expectedProducts.isEmpty() ? expectedProducts.get(0).getId() : UUID.randomUUID();
        Product results = productQueryDataFetcher.productById(expectedProductId);
        if (results != null) {
            assertEquals(expectedProductId, results.getId());
        }
    }

    /**
     * Tests the {@code productByName} method of {@code ProductQueryDataFetcher}.
     *
     * @param expectedProducts The expected list of products.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void testProductByName(List<Product> expectedProducts, String testName) {
        MockProductService productService = new MockProductService(expectedProducts);
        ProductQueryDataFetcher productQueryDataFetcher = new ProductQueryDataFetcher(productService);
        String expectedProductName = !expectedProducts.isEmpty() ? expectedProducts.get(0).getName() : null;
        if (expectedProductName != null) {
            Product results = productQueryDataFetcher.productByName(expectedProductName);
            assertEquals(expectedProductName, results.getName());
        }
    }

    /**
     * Tests the {@code productsByPriceLessThanOrEqualTo} method of {@code ProductQueryDataFetcher}.
     *
     * @param expectedProducts The expected list of products.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void testProductsByPriceLessThanOrEqualTo(List<Product> expectedProducts, String testName) {
        MockProductService productService = new MockProductService(expectedProducts);
        ProductQueryDataFetcher productQueryDataFetcher = new ProductQueryDataFetcher(productService);
        if (expectedProducts.isEmpty()) {
            List<Product> results = productQueryDataFetcher.productsByPriceBetween(500.00, 100.00);
            assertEquals(expectedProducts.size(), results.size());
        } else if (expectedProducts.size() == 1) {
            Product expectedProduct = expectedProducts.get(0);
            List<Product> results = productQueryDataFetcher.productsByPriceLessThanOrEqualTo(500.00);
            Product resultProduct = results.get(0);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getPrice(), resultProduct.getPrice());
            assertEquals(expectedProduct.getCurrencyCode(), resultProduct.getCurrencyCode());
        } else {
            Product expectedProduct = expectedProducts.stream().filter((product) -> product.getPrice().equals(100.00)).findFirst().get();
            List<Product> results = productQueryDataFetcher.productsByPriceLessThanOrEqualTo(200.00);
            Product resultProduct = results.get(0);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getPrice(), resultProduct.getPrice());
            assertEquals(expectedProduct.getCurrencyCode(), resultProduct.getCurrencyCode());

        }
    }

    /**
     * Tests the {@code productsByPriceGreaterThanOrEqualTo} method of {@code ProductQueryDataFetcher}.
     *
     * @param expectedProducts The expected list of products.
     * @param testName The name of the test.
     */
    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void testProductsByPriceGreaterThanOrEqualTo(List<Product> expectedProducts, String testName) {
        MockProductService productService = new MockProductService(expectedProducts);
        ProductQueryDataFetcher productQueryDataFetcher = new ProductQueryDataFetcher(productService);
        if (expectedProducts.isEmpty()) {
            List<Product> results = productQueryDataFetcher.productsByPriceBetween(500.00, 100.00);
            assertEquals(expectedProducts.size(), results.size());
        } else if (expectedProducts.size() == 1) {
            Product expectedProduct = expectedProducts.get(0);
            List<Product> results = productQueryDataFetcher.productsByPriceGreaterThanOrEqualTo(50.00);
            Product resultProduct = results.get(0);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getPrice(), resultProduct.getPrice());
            assertEquals(expectedProduct.getCurrencyCode(), resultProduct.getCurrencyCode());
        } else {
            Product expectedProduct = expectedProducts.stream().filter((product) -> product.getPrice().equals(100.00)).findFirst().get();
            List<Product> results = productQueryDataFetcher.productsByPriceLessThanOrEqualTo(105.00);
            Product resultProduct = results.get(0);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getPrice(), resultProduct.getPrice());
            assertEquals(expectedProduct.getCurrencyCode(), resultProduct.getCurrencyCode());

        }
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("mockProductsLists")
    void testProductsByPriceBetween(List<Product> expectedProducts, String testName) {
        MockProductService productService = new MockProductService(expectedProducts);
        ProductQueryDataFetcher productQueryDataFetcher = new ProductQueryDataFetcher(productService);
        if (expectedProducts.isEmpty()) {
            List<Product> results = productQueryDataFetcher.productsByPriceBetween(500.00, 100.00);
            assertEquals(expectedProducts.size(), results.size());
        } else if (expectedProducts.size() == 1) {
            Product expectedProduct = expectedProducts.get(0);
            List<Product> results = productQueryDataFetcher.productsByPriceBetween(50.00, 200.00);
            Product resultProduct = results.get(0);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getPrice(), resultProduct.getPrice());
            assertEquals(expectedProduct.getCurrencyCode(), resultProduct.getCurrencyCode());
        } else {
            Product expectedProduct = expectedProducts.stream().filter((product) -> product.getPrice().equals(300.00)).findFirst().get();
            List<Product> results = productQueryDataFetcher.productsByPriceBetween(200.00, 400.00);
            Product resultProduct = results.get(0);
            assertEquals(expectedProduct.getId(), resultProduct.getId());
            assertEquals(expectedProduct.getName(), resultProduct.getName());
            assertEquals(expectedProduct.getPrice(), resultProduct.getPrice());
            assertEquals(expectedProduct.getCurrencyCode(), resultProduct.getCurrencyCode());

        }
    }

}

package com.mikejacks.international_currency_converter.landedcost.client;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Client class for interacting with the Localization GraphQL API.
 *
 * <p>This class provides methods to fetch currency conversion data from a GraphQL endpoint.
 * It utilizes Spring's {@code WebClient} to send requests and handle responses.</p>
 */
@Component
public class LocalizationGraphQLClient {
    private static final Logger logger = Logger.getLogger(LocalizationGraphQLClient.class.getName());
    private final String endpoint;

    /**
     * Constructs a new {@code LocalizationGraphQLClient} with the specified GraphQL endpoint.
     *
     * @param endpoint The URL of the GraphQL endpoint.
     */
    @Autowired
    public LocalizationGraphQLClient(@Value("${localization.graphql.endpoint}") String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Fetches currency conversion data for the specified base and target currency codes.
     *
     * <p>This method sends a GraphQL query to the configured endpoint to retrieve currency conversion data.
     * It returns a {@code Currency} object containing the conversion details.</p>
     *
     * @param baseCode The base currency code.
     * @param targetCode The target currency code.
     * @return A {@code Currency} object containing the conversion details.
     * @throws RuntimeException if the response format is invalid or if an error occurs during the request.
     */
    public Currency getCurrency(String baseCode, String targetCode) {
        String query = "{ currency(baseCode: \"" + baseCode + "\", targetCode: \"" + targetCode + "\") { baseCode targetCode conversionRate } }";
        WebClient webClient = WebClient.create(endpoint);

        Mono<Map<String, Object>> response = webClient.post()
                .bodyValue(Collections.singletonMap("query", query))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});

        Map<String, Object> data = response.block();
        logger.info("GraphQL Response: " + data.toString());
        Optional<Map<String, Object>> optionalCurrencyData = castToMap(data.get("data"));
        if (optionalCurrencyData.isPresent()) {
            Map<String, Object> currencyData = (Map<String, Object>) optionalCurrencyData.get().get("currency");
            Currency currency = new Currency((UUID) currencyData.get("id"), (String) currencyData.get("baseCode"), (String) currencyData.get("targetCode"), (Double) currencyData.get("conversionRate"));
            return currency;
        } else {
            throw new RuntimeException("Invalid response format");
        }
    }

    /**
     * Casts an object to a map if possible.
     *
     * <p>This method attempts to cast the given object to a {@code Map<String, Object>}. If the object is an instance of
     * {@code Map}, it is cast and returned. Otherwise, an empty {@code Optional} is returned.</p>
     *
     * @param obj The object to cast.
     * @return An {@code Optional} containing the cast map, or empty if the cast is not possible.
     */
    @SuppressWarnings("unchecked")
    public static Optional<Map<String, Object>> castToMap(Object obj) {
        if (obj instanceof Map) {
            return Optional.of((Map<String, Object>) obj);
        }
        return Optional.empty();
    }
}

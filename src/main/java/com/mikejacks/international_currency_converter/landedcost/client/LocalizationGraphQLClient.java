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

@Component
public class LocalizationGraphQLClient {
    private static final Logger logger = Logger.getLogger(LocalizationGraphQLClient.class.getName());
    private final String endpoint;

    @Autowired
    public LocalizationGraphQLClient(@Value("${localization.graphql.endpoint}") String endpoint) {
        this.endpoint = endpoint;
    }

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

    @SuppressWarnings("unchecked")
    public static Optional<Map<String, Object>> castToMap(Object obj) {
        if (obj instanceof Map) {
            return Optional.of((Map<String, Object>) obj);
        }
        return Optional.empty();
    }
}

package com.mikejacks.international_currency_converter.localization.service.impl;

import com.mikejacks.international_currency_converter.localization.model.CurrencyCreateInput;
import com.mikejacks.international_currency_converter.localization.model.CurrencyUpdateInput;
import com.mikejacks.international_currency_converter.localization.service.CurrencyService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.repository.CurrencyRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
public class BaseCurrencyService implements CurrencyService {
    @Value("${FREECURRENCY_API_KEY}")
    private String FREECURRENCY_API_KEY;

    @Value("${FREECURRENCY_API_URL_HOST}")
    private String FREECURRENCY_API_URL_HOST;


    private CurrencyRepository currencyRepository;

    @Autowired
    public BaseCurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    // Query Methods

    /**
     * Retrieves a list of all currencies.
     *
     * <p>This method fetches all {@code Currency} objects from the repository and returns them as a list.</p>
     *
     * @return A list of all {@code Currency} objects.
     */
    @Override
    public List<Currency> currencies() {
        return currencyRepository.findAll();
    }


    /**
     * Retrieves a currency based on the specified base code and target code.
     *
     * <p>This method fetches a {@code Currency} object from the repository based on the provided base code and target code.
     * If a currency with the specified base code and target code is found, it is returned. Otherwise, {@code null} is returned.</p>
     *
     * @param baseCode The base code of the currency to retrieve. Must not be null.
     * @param targetCode The target code of the currency to retrieve. Must not be null.
     * @return The {@code Currency} object matching the specified base code and target code, or {@code null} if no such currency is found.
     */
    @Override
    public Currency currency(@NotNull String baseCode,@NotNull String targetCode) {
        return currencyRepository.findCurrenciesByBaseCodeAndTargetCode(baseCode, targetCode).orElse(null);
    }

    /**
     * Retrieves a list of currencies based on the specified base code or target code.
     *
     * <p>This method fetches currencies from the database based on the provided base code or target code.
     * If both base code and target code are provided, an {@code IllegalArgumentException} is thrown.
     * If only the base code is provided, it returns currencies with the specified base code.
     * If only the target code is provided, it returns currencies with the specified target code.
     * If neither base code nor target code is provided, it returns all currencies.</p>
     *
     * @param baseCode The base code of the currencies to retrieve. Can be null.
     * @param targetCode The target code of the currencies to retrieve. Can be null.
     * @return A list of {@code Currency} objects matching the specified criteria, or all currencies if no criteria are specified.
     * @throws IllegalArgumentException if both base code and target code are specified.
     */
    @Override
    public List<Currency> currencies(String baseCode, String targetCode) {
        if (baseCode != null && targetCode != null) {
            throw new IllegalArgumentException("Only one of either baseCode or targetCode can be specified. Not both.");
        } else if (baseCode != null) {
            return currencyRepository.findCurrenciesByBaseCode(baseCode).stream().toList();
        } else if (targetCode != null) {
            return currencyRepository.findCurrenciesByTargetCode(targetCode).stream().toList();
        } else {
            return currencyRepository.findAll();
        }
    }


    // Mutation Methods

    /**
     * Adds a new currency to the database.
     *
     * <p>This method creates a new {@code Currency} object using the details provided in the {@code CurrencyCreateInput} object.
     * It checks if a currency with the same base code and target code already exists in the repository. If such a currency exists,
     * an {@code IllegalArgumentException} is thrown. Otherwise, the new currency is saved in the repository.</p>
     *
     * @param currencyCreateInput A {@code CurrencyCreateInput} object containing the details of the new currency to be added.
     * @return The newly added {@code Currency} object.
     * @throws IllegalArgumentException if a currency with the same base code and target code already exists.
     */
    @Override
    public Currency addCurrency(@NotNull CurrencyCreateInput currencyCreateInput) {
        Currency newCurrency = new Currency(currencyCreateInput.getBaseCode(), currencyCreateInput.getTargetCode(), currencyCreateInput.getConversionRate());
        Currency existingCurrency = currencyRepository.findCurrenciesByBaseCodeAndTargetCode(currencyCreateInput.getBaseCode(), currencyCreateInput.getTargetCode()).orElse(null);
        if (existingCurrency != null) {
            throw new IllegalArgumentException("Currency wth base code: " + currencyCreateInput.getBaseCode() + ", and target code:" + currencyCreateInput.getTargetCode() + " already exists.");
        }
        return currencyRepository.save(newCurrency);
    }

    /**
     * Updates an existing currency's details by its ID.
     *
     * <p>This method finds an existing {@code Currency} object by its ID. If the currency exists, it updates the base code,
     * target code, and conversion rate if they are provided in the {@code CurrencyUpdateInput} object. If the currency does
     * not exist, an {@code IllegalArgumentException} is thrown. The updated currency is then saved to the repository and returned.</p>
     *
     * @param currencyId The ID of the currency to be updated.
     * @param currencyUpdateInput A {@code CurrencyUpdateInput} object containing the updated details of the currency.
     * @return The updated {@code Currency} object.
     * @throws IllegalArgumentException if a currency with the specified ID does not exist.
     */

    @Override
    public Currency updateCurrencyById(UUID currencyId, CurrencyUpdateInput currencyUpdateInput) {
       Currency existingCurrency = currencyRepository.findById(currencyId).orElse(null);
       if (existingCurrency == null) {
           throw new IllegalArgumentException("Currency with id " + currencyId + " does not exist.");
       }
       if (currencyUpdateInput.getBaseCode() != null) {
           existingCurrency.setBaseCode(currencyUpdateInput.getBaseCode());
       }
       if (currencyUpdateInput.getTargetCode() != null) {
           existingCurrency.setTargetCode(currencyUpdateInput.getTargetCode());
       }
       if (currencyUpdateInput.getConversionRate() != null) {
           existingCurrency.setConversionRate(currencyUpdateInput.getConversionRate());
       }
       return currencyRepository.save(existingCurrency);
    }

    @Override
    public Currency updateCurrencyRateToLiveById(UUID currencyId) {
       Currency existingCurrency = currencyRepository.findById(currencyId).orElse(null);
       if (existingCurrency == null) {
           throw new IllegalArgumentException("Currency with id " + currencyId + " does not exist.");
       }
       String baseCode = existingCurrency.getBaseCode();
       String targetCode = existingCurrency.getTargetCode();
       String urlString = FREECURRENCY_API_URL_HOST + FREECURRENCY_API_KEY + "&base_currency=" + baseCode + "&currencies=" + targetCode;

       try {
           String response = getJsonResponse(urlString);
           JSONObject responseData = parseJsonResponse(response);
           Double liveConversionRate = responseData.getDouble(targetCode);
           existingCurrency.setConversionRate(liveConversionRate);
           return currencyRepository.save(existingCurrency);
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException("Failed to update currency to live rate: " + e.getMessage());
       }
    }

    private @NotNull String getJsonResponse(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }

    private JSONObject parseJsonResponse(String jsonResponse) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        return dataObject;
    }
}

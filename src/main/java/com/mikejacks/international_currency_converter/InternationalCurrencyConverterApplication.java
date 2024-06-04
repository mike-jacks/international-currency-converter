package com.mikejacks.international_currency_converter;

import com.mikejacks.international_currency_converter.landedcost.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mikejacks.international_currency_converter.landedcost.entity.Country;
import com.mikejacks.international_currency_converter.localization.entity.Currency;
import com.mikejacks.international_currency_converter.localization.repository.CurrencyRepository;

@SpringBootApplication
public class InternationalCurrencyConverterApplication {
	public static void main(String[] args) {
		SpringApplication.run(InternationalCurrencyConverterApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CurrencyRepository currencyRepository, CountryRepository countryRepository) {
		return args -> {
			// Clear existing entries
//            countryRepository.deleteAll();
//            currencyRepository.deleteAll();

            // Add countries (if necessary)
            if (countryRepository.count() == 0) {
                countryRepository.save(new Country("United States of America", "USD", 3.0, 0.0));
                countryRepository.save(new Country("European Union", "EUR", 5.0, 20.0));
                countryRepository.save(new Country("United Kingdom", "GBP", 4.5, 20.0));
                countryRepository.save(new Country("Japan", "JPY", 2.5, 10.0));
                countryRepository.save(new Country("Switzerland", "CHF", 3.0, 7.7));
                countryRepository.save(new Country("Canada", "CAD", 3.5, 5.0));
            }

            if (currencyRepository.count() == 0) {
                // Add new currency conversion rates
                currencyRepository.save(new Currency(null, "EUR", "USD", 1.10));
                currencyRepository.save(new Currency(null, "USD", "EUR", 0.91));

                currencyRepository.save(new Currency(null, "GBP", "USD", 1.25));
                currencyRepository.save(new Currency(null, "USD", "GBP", 0.80));

                currencyRepository.save(new Currency(null, "JPY", "USD", 0.0072));
                currencyRepository.save(new Currency(null, "USD", "JPY", 138.89));

                currencyRepository.save(new Currency(null, "CHF", "USD", 1.10));
                currencyRepository.save(new Currency(null, "USD", "CHF", 0.91));

                currencyRepository.save(new Currency(null, "CAD", "USD", 0.74));
                currencyRepository.save(new Currency(null, "USD", "CAD", 1.35));
            }
		};
	}
}

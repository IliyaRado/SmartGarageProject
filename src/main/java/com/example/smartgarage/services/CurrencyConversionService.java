package com.example.smartgarage.services;

import com.example.smartgarage.helpers.OpenExchangeRatesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyConversionService {

    @Value("${openexchangerates.api.url}")
    private String apiUrl;

    @Value("${openexchangerates.api.key}")
    private String apiKey;

    @Value("${openexchangerates.base.currency}")
    private String baseCurrency;

    private final RestTemplate restTemplate;

    public CurrencyConversionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double convertCurrency(double amount, String toCurrency) {
        String url = String.format("%s?app_id=%s", apiUrl, apiKey);
        ResponseEntity<OpenExchangeRatesResponse> response = restTemplate.getForEntity(url, OpenExchangeRatesResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            OpenExchangeRatesResponse rates = response.getBody();
            if (rates != null && rates.getRates().containsKey(toCurrency)) {
                double conversionRate = rates.getRates().get(toCurrency);
                return amount * conversionRate;
            }
        }
        throw new RuntimeException("Currency conversion failed.");
    }

}

package com.example.smartgarage.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class OpenExchangeRatesResponse {

    @JsonProperty("rates")
    private Map<String, Double> rates;

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

}

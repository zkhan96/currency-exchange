package org.example.model;

import java.util.Map;

public record Result(Map<String, ExchangeCSVLine> currencyCodeToExchangeMap, javax.money.NumberValue convertedCurrency) { }
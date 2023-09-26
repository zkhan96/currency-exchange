package org.example.model;

import java.math.BigDecimal;

public record ExchangeCSVLine (String country, String name, String code, BigDecimal rate) {}


package org.example.exchange;

import org.example.model.ExchangeCSVLine;
import org.example.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryException;
import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeServiceTest {

    Map<String, ExchangeCSVLine> currencyCodeToExchangeMap;

    private CurrencyExchangeService underTest;

    @BeforeEach
    void setUp() {
        currencyCodeToExchangeMap = new HashMap<>();
        currencyCodeToExchangeMap.put("AED", new ExchangeCSVLine("United Arab Emirates", "Dirhams",
                "AED", new BigDecimal("7.2104")));
        currencyCodeToExchangeMap.put("AUD", new ExchangeCSVLine("Australia", "Dollars",
                "AUD", new BigDecimal("1.51239")));
        underTest = new CurrencyExchangeService(currencyCodeToExchangeMap);
    }

    @Test
    void shouldExchange() {
        Result resultAED = underTest.exchange("1.00", "AUD", "AED");

        assertEquals("4.77", resultAED.convertedCurrency().toString());
    }

    @Test
    void shouldThrowNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> underTest.exchange("1badcurrency", "AED", "AUD"));
    }

    @Test
    void shouldThrowUnknownCurrencyException() {
        assertThrows(UnknownCurrencyException.class, () -> underTest.exchange("1.00", "BAD", "AUD"));
    }

    @Test
    void shouldThrowMonetaryException() {
        assertThrows(MonetaryException.class, () -> underTest.exchange("1.00", "AED", "BAD"));
    }
}
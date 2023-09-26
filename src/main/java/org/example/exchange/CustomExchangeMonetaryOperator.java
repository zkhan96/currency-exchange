package org.example.exchange;

import org.example.model.ExchangeCSVLine;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import java.util.Map;
import java.util.Objects;

public class CustomExchangeMonetaryOperator implements MonetaryOperator {


    private final String sourceCurrencyCode;
    private final String targetCurrencyCode;
    private final Map<String, ExchangeCSVLine> exchangeRatesByCountryCode;

    public CustomExchangeMonetaryOperator(String sourceCurrencyCode, String targetCurrencyCode, Map<String, ExchangeCSVLine> exchangeRatesByCountryCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.exchangeRatesByCountryCode = exchangeRatesByCountryCode;
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount amount) {
        MonetaryAmount amountInGBP = amount.divide(exchangeRatesByCountryCode.get(sourceCurrencyCode).rate());
        ExchangeCSVLine exchangeCSVLine = exchangeRatesByCountryCode.get(targetCurrencyCode);
        Objects.requireNonNull(exchangeCSVLine, "Exchange rate for %s does not exist".formatted(targetCurrencyCode));
        return amountInGBP.multiply(exchangeCSVLine.rate());
    }
}

package org.example.exchange;

import org.example.model.ExchangeCSVLine;
import org.example.model.Result;

import javax.money.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;


public class CurrencyExchangeService {
    private final Map<String, ExchangeCSVLine> currencyCodeToExchangeMap;

    public CurrencyExchangeService(Map<String, ExchangeCSVLine> records) {
        currencyCodeToExchangeMap = records;

    }

    public Result exchange(String currencyAmount, String sourceCurrencyCode, String targetCurrencyCode) {
        BigDecimal amountToConvert = parseCurrencyToConvert(currencyAmount);
        MonetaryAmount sourceCurrency = parseSourceCurrency(sourceCurrencyCode, amountToConvert);


        CustomExchangeMonetaryOperator operator = new CustomExchangeMonetaryOperator(sourceCurrencyCode,
                targetCurrencyCode, currencyCodeToExchangeMap);

        NumberValue convertedCurrency = getConvertedCurrency(sourceCurrency, operator).getNumber()
                .round(new MathContext(3, RoundingMode.UP));

        return new Result(currencyCodeToExchangeMap, convertedCurrency);
    }


    private BigDecimal parseCurrencyToConvert(String currencyAmount) {
        BigDecimal amountToConvert = null;
        try {
            amountToConvert = new BigDecimal(currencyAmount);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Please provide a valid currency as the first argument in the format x.yy");
            throw numberFormatException;
        }
        return amountToConvert;
    }

    private MonetaryAmount parseSourceCurrency(String sourceCurrencyCode, BigDecimal amountToConvert) {
        MonetaryAmount sourceCurrency = null;
        try {
            sourceCurrency = Monetary.getDefaultAmountFactory()
                    .setCurrency(sourceCurrencyCode)
                    .setNumber(amountToConvert)
                    .create();
        } catch (UnknownCurrencyException unknownCurrencyException) {
            System.out.println("Please provide a valid currency code as the second argument that conforms to ISO 4217");
            throw unknownCurrencyException;
        }
        return sourceCurrency;
    }

    private MonetaryAmount getConvertedCurrency(MonetaryAmount sourceCurrency, CustomExchangeMonetaryOperator operator) {
        MonetaryAmount convertedCurrency = null;
        try {
            convertedCurrency = sourceCurrency.with(operator);
        } catch (MonetaryException e) {
            System.out.println("Please provide a proper currency code as the third argument that conforms to ISO 4217");
            throw e;
        }
        return convertedCurrency;
    }
}

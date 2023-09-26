package org.example;

import org.example.csv.CSVLoader;
import org.example.exchange.CurrencyExchangeService;
import org.example.model.Result;

public class CurrencyExchangeApplication {
    private static final String CSV_FILE_PATH = "src/main/resources/rates-to-gbp.csv";

    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            System.out.println("Please provide 3 arguments: x.yy FROM_CODE TO_CODE");
            throw new IllegalArgumentException("Please provide 3 arguments: x.yy FROM_CODE TO_CODE");
        }

        String currencyAmount = args[0];
        String sourceCurrencyCode = args[1];
        String targetCurrencyCode = args[2];

        Result result = new CurrencyExchangeService(new CSVLoader(CSV_FILE_PATH).getRecords()).exchange(currencyAmount, sourceCurrencyCode, targetCurrencyCode);

        System.out.printf("%s,%s,%s%n", result.convertedCurrency(),
                result.currencyCodeToExchangeMap().get(targetCurrencyCode).country(), targetCurrencyCode);

    }
}
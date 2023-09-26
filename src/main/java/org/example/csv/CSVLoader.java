package org.example.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.ExchangeCSVLine;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CSVLoader {

    private static final int COUNTRY_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int CODE_INDEX = 2;
    private static final int RATE_INDEX = 3;
    private final HashMap<String, ExchangeCSVLine> records;

    public CSVLoader(String filePath) throws CsvValidationException, IOException {
        records = new HashMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            csvReader.skip(1);
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.put(values[CODE_INDEX], new ExchangeCSVLine(values[COUNTRY_INDEX], values[NAME_INDEX],
                        values[CODE_INDEX], new BigDecimal(values[RATE_INDEX])));
            }
        }
    }

    public Map<String, ExchangeCSVLine> getRecords() {
        return records;
    }
}

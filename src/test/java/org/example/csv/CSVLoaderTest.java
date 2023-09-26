package org.example.csv;

import com.opencsv.exceptions.CsvValidationException;
import org.example.model.ExchangeCSVLine;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVLoaderTest {
    private static final String CSV_TEST_FILE_PATH = "src/test/resources/rates-to-gbp.csv";
    private static final String BAD_CSV_TEST_FILE_PATH = "src/test/resources/bad.csv";


    @Test
    void shouldExtractCSVWithNoErrors() throws CsvValidationException, IOException {
        CSVLoader csvLoader = new CSVLoader(CSV_TEST_FILE_PATH);

        Map<String, ExchangeCSVLine> records = csvLoader.getRecords();

        assertNotNull(records);
        assertFalse(records.isEmpty());

        ExchangeCSVLine expected = new ExchangeCSVLine("UAE", "Dirham",
                "AED", new BigDecimal("4.6386"));
        ExchangeCSVLine result = records.get("AED");
        assertEquals(expected, result);
    }

    @Test
    void shouldThrowCSVValidationException() {
        assertThrows(IOException.class, () -> new CSVLoader(BAD_CSV_TEST_FILE_PATH));
    }
}
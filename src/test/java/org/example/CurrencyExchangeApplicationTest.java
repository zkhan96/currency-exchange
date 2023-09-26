package org.example;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryException;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyExchangeApplicationTest {

    @Test
    void shouldConvertCurrencyBGNToAED() throws Exception {
//        Given
        String sourceCurrency = "2.61";
        String bulgarianSourceCurrencyCode = "BGN";
        String emiratiDirhamTargetCurrencyCode = "AED";

//        When
        String result = tapSystemOut(() -> CurrencyExchangeApplication.main(
                new String[]{sourceCurrency, bulgarianSourceCurrencyCode, emiratiDirhamTargetCurrencyCode}));

//        Then
        assertEquals("5.30,UAE,AED", result.trim());
    }

    @Test
    void shouldThrowExceptionWhenNotExactlyThreeArgs() {
//        Given
        String sourceCurrency = "2.61";
        String unknownCurrencyCode = "BGN";

//        When
//        Then
        assertThrows(IllegalArgumentException.class, () -> CurrencyExchangeApplication.main(
                new String[]{sourceCurrency, unknownCurrencyCode}));
    }


    @Test
    void shouldThrowExceptionWhenCurrencyFormatIsInvalid() {
//        Given
        String sourceCurrency = "INVALID_NUMBER";
        String source = "BGN";
        String target = "AED";

//        When
//        Then
        assertThrows(NumberFormatException.class, () -> CurrencyExchangeApplication.main(
                new String[]{sourceCurrency, source, target}));

    }

    @Test
    void shouldThrowExceptionWhenConvertCurrencyUnknownToBGNCurrency() {
//        Given
        String sourceCurrency = "2.61";
        String bulgarianTargetCurrencyCode = "BGN";
        String unknownSourceCurrencyCode = "IAMATEAPOT";

//        When
//        Then
        assertThrows(MonetaryException.class, () -> CurrencyExchangeApplication.main(
                new String[]{sourceCurrency, unknownSourceCurrencyCode, bulgarianTargetCurrencyCode}));

    }

    @Test
    void shouldThrowExceptionWhenConvertCurrencyBGNToUnknownCurrency() {
//        Given
        String sourceCurrency = "2.61";
        String bulgarianSourceCurrencyCode = "BGN";
        String unknownTargetCurrencyCode = "IAMATEAPOT";

//        When
//        Then
        assertThrows(MonetaryException.class, () -> CurrencyExchangeApplication.main(
                new String[]{sourceCurrency, bulgarianSourceCurrencyCode, unknownTargetCurrencyCode}));
    }
}
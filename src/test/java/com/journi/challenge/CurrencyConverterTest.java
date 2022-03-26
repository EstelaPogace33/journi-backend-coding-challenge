package com.journi.challenge;

import com.journi.challenge.controllers.ProductsController;
import com.journi.challenge.controllers.PurchasesController;
import com.journi.challenge.repositories.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterTest {

    private CurrencyConverter currencyConverter = new CurrencyConverter();


    @Test
    void findCurrencyCodeForSupportedCountry() {
        assertEquals("EUR", currencyConverter.getCurrencyForCountryCode("AT"));
        assertEquals("EUR", currencyConverter.getCurrencyForCountryCode("DE"));
        assertEquals("EUR", currencyConverter.getCurrencyForCountryCode("FR"));
        assertEquals("BRL", currencyConverter.getCurrencyForCountryCode("BR"));
        assertEquals("GBP", currencyConverter.getCurrencyForCountryCode("GB"));
    }

    @Test
    void findCurrencyCodeForNonSupportedCountry() {
        assertEquals("EUR", currencyConverter.getCurrencyForCountryCode("CH"));
        assertEquals("EUR", currencyConverter.getCurrencyForCountryCode("CL"));
        assertEquals("EUR", currencyConverter.getCurrencyForCountryCode("AR"));
        assertEquals("EUR", currencyConverter.getCurrencyForCountryCode("FI"));
    }

    @Test
    void convertEurValueToSupportedCurrency() {
        assertEquals(25.0, currencyConverter.convertEurToCurrency("EUR", 25.0));
        assertEquals(25.0 * 5.1480, currencyConverter.convertEurToCurrency("BRL", 25.0));
    }

}
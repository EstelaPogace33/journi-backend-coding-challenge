package com.journi.challenge.controllers;

import com.journi.challenge.CurrencyConverter;
import com.journi.challenge.models.Purchase;
import com.journi.challenge.models.PurchaseRequest;
import com.journi.challenge.models.PurchaseStats;
import com.journi.challenge.repositories.PurchasesRepository;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PurchasesController {

    @Inject
    private PurchasesRepository purchasesRepository;

    @GetMapping("/purchases/statistics")
    public PurchaseStats getStats() {
        return purchasesRepository.getLast30DaysStats();
    }

    @PostMapping("/purchases")
    public Purchase save(@RequestBody PurchaseRequest purchaseRequest) {

        CurrencyConverter currencyConverter = new CurrencyConverter();
        Double convertedAmountOfPurchase=currencyConverter.convertEurToCurrency(purchaseRequest.getCurrencyCode(),purchaseRequest.getAmount());
        Purchase newPurchase = new Purchase(
                purchaseRequest.getInvoiceNumber(),
                LocalDateTime.parse(purchaseRequest.getDateTime(), DateTimeFormatter.ISO_DATE_TIME),
                purchaseRequest.getProductIds(),
                purchaseRequest.getCustomerName(),
                convertedAmountOfPurchase
        );
        purchasesRepository.save(newPurchase);

        return newPurchase;
    }
}

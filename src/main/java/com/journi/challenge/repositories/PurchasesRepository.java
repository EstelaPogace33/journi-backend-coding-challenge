package com.journi.challenge.repositories;

import com.journi.challenge.models.Purchase;
import com.journi.challenge.models.PurchaseStats;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Named
@Singleton
public class PurchasesRepository {

    private final List<Purchase> allPurchases = new ArrayList<>();

    public List<Purchase> list() {
        return allPurchases;
    }

    public void save(Purchase purchase) {
        allPurchases.add(purchase);
    }

    public PurchaseStats getLast30DaysStats() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE.withZone(ZoneId.of("UTC"));

        LocalDateTime start = LocalDate.now().atStartOfDay().minusDays(30);
        List<Purchase> recentPurchases=new ArrayList<>();
        if( !allPurchases.isEmpty()){
        recentPurchases = allPurchases
                .stream()
                .filter(p -> p.getTimestamp().isAfter(start))
                .sorted(Comparator.comparing(Purchase::getTimestamp))
                .collect(Collectors.toList());
        }

        long countPurchases = recentPurchases.size();
        PurchaseStats purchaseStatsResult = null;
        
        if(recentPurchases.size()>1){
            double totalAmountPurchases = recentPurchases.stream().mapToDouble(Purchase::getTotalValue).sum();
            purchaseStatsResult= new PurchaseStats(
                    formatter.format(recentPurchases.get(0).getTimestamp()),
                    formatter.format(recentPurchases.get(recentPurchases.size() - 1).getTimestamp()),
                    countPurchases,
                    totalAmountPurchases,
                    totalAmountPurchases / countPurchases,
                    recentPurchases.stream().mapToDouble(Purchase::getTotalValue).min().orElse(0.0),
                    recentPurchases.stream().mapToDouble(Purchase::getTotalValue).min().orElse(0.0)
            );
        }
        return  purchaseStatsResult;
    }
}

package com.journi.challenge.controllers;

import com.journi.challenge.CurrencyConverter;
import com.journi.challenge.models.Product;
import com.journi.challenge.repositories.ProductsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductsController {

    @Inject
    private ProductsRepository productsRepository;

    @GetMapping("/products")
    public List<Product> list(@RequestParam(name = "countryCode", defaultValue = "AT") String countryCode) {
        if(countryCode==null){
            countryCode="AT";
        }
        CurrencyConverter currencyConverter= new CurrencyConverter();
        List<Product> listOfProductsToBeReturned= new ArrayList<>();
        if(productsRepository!=null){
        for (Product product : productsRepository.list()){
            String currencyFromCountryCode=currencyConverter.getCurrencyForCountryCode(countryCode);
            Double priceToLocalCurrency=currencyConverter.convertEurToCurrency(currencyFromCountryCode, product.getPrice());
            Product copyProduct= new Product(product.getId(),product.getDescription(),priceToLocalCurrency);
            listOfProductsToBeReturned.add(copyProduct);
        }
        }

        return listOfProductsToBeReturned;
    }
}

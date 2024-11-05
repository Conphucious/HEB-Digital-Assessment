package com.github.conphucious.pricecomparator;

import com.github.conphucious.pricecomparator.controller.PriceComparisonController;
import com.github.conphucious.pricecomparator.service.DefaultMerchantService;
import com.github.conphucious.pricecomparator.service.DefaultPriceComparisonService;
import com.github.conphucious.pricecomparator.service.DefaultRequestService;
import com.github.conphucious.pricecomparator.service.MerchantService;
import com.github.conphucious.pricecomparator.service.PriceComparisonService;
import com.github.conphucious.pricecomparator.service.RequestService;

public class Application {
    public static void main(String[] args) {
        RequestService requestService = new DefaultRequestService();
        MerchantService merchantService = new DefaultMerchantService();
        PriceComparisonService priceComparisonService = new DefaultPriceComparisonService();
        PriceComparisonController priceComparisonController =
                new PriceComparisonController(requestService, merchantService, priceComparisonService);
        String urls = priceComparisonController.determineUrlOfLowestUpcPriceWithAvailability(101);
        System.out.println("URLs: " + urls);
    }
}

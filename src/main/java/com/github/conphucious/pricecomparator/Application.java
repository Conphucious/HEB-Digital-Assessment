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
        // Normally DI such as GUICE or Spring DI
        RequestService requestService = new DefaultRequestService();
        MerchantService merchantService = new DefaultMerchantService();
        PriceComparisonService priceComparisonService = new DefaultPriceComparisonService();
        PriceComparisonController priceComparisonController =
                new PriceComparisonController(requestService, merchantService, priceComparisonService);

        // Requirement output:
        int upc = 101;
        String urls = priceComparisonController.determineUrlOfLowestUpcPriceWithAvailability(upc);
        System.out.println("Lowest price available vendor URL(s) for upc '" + upc + "' at '" + System.currentTimeMillis() + "': '" + urls + "'");
    }
}

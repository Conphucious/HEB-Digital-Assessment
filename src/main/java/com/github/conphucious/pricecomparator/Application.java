package com.github.conphucious.pricecomparator;

import com.github.conphucious.pricecomparator.controller.PriceComparisonController;

public class Application {
    public static void main(String[] args) {
        PriceComparisonController priceComparisonController = new PriceComparisonController();
        String urls = priceComparisonController.determineUrlOfLowestUpcPriceWithAvailability(101);
        System.out.println(urls);
    }
}

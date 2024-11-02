package com.github.conphucious.pricecomparator;

import com.github.conphucious.pricecomparator.controller.PriceComparisonController;

import java.util.Optional;

public class Application {
    public static void main(String[] args) {
        PriceComparisonController priceComparisonController = new PriceComparisonController();
        Optional<String> url = priceComparisonController.determineUrlOfLowestUpcPriceWithAvailability(101);
        System.out.println(url);
    }
}

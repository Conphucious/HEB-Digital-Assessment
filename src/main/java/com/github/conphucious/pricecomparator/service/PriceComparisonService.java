package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.model.UPCData;

/**
 * Price comparison service interface which defines the comparison methods.
 */
public interface PriceComparisonService {

    /**
     * Finds the lowest price for a given UPC with availability at a merchant.
     *
     * @param upc
     * @return UPCData POJO
     */
    UPCData findLowestPriceAvailable(int upc);

}

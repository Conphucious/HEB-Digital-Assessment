package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.model.UpcData;

import java.util.List;

/**
 * Price comparison service interface which defines the comparison methods.
 */
public interface PriceComparisonService {

    /**
     * Finds lowest priced available upc item in a given upc data list if one exists.
     * If multiple lowest price items exists (e.g. $1 at 'A' and $1 at 'B'), then multiple UpcData objects will be returned.
     *
     * @param upcDataList
     * @return List of UpcData.
     */
    List<UpcData> findLowestPriceAvailableUpcData(List<UpcData> upcDataList);

}

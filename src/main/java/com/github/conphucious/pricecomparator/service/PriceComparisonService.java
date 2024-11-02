package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.model.UPCData;

import java.util.List;
import java.util.Optional;

/**
 * Price comparison service interface which defines the comparison methods.
 */
public interface PriceComparisonService {

    Optional<UPCData> findLowestPriceAvailableUpcData(List<UPCData> upcDataList);

}

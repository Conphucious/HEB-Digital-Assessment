package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.model.UPCData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DefaultPriceComparisonService implements PriceComparisonService {
    @Override
    public Optional<UPCData> findLowestPriceAvailableUpcData(List<UPCData> upcDataList) {
        return upcDataList
                .stream()
                .filter(UPCData::isAvailable)
                .min(Comparator.comparingDouble(UPCData::getPrice));
    }
}

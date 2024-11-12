package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.model.UpcData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPriceComparisonService implements PriceComparisonService {

    @Override
    public List<UpcData> findLowestPriceAvailableUpcData(List<UpcData> upcDataList) {
        List<UpcData> sortedPriceUpcDataList = upcDataList
                .stream()
                .filter(UpcData::isAvailable)
                .sorted(Comparator.comparingDouble(UpcData::getPrice))
                .collect(Collectors.toList());

        return sortedPriceUpcDataList
                .stream()
                .filter(upcd -> upcd.getPrice() == sortedPriceUpcDataList.get(0).getPrice())
                .collect(Collectors.toList());
    }

}

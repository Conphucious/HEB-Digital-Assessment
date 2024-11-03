package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.model.UpcData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultPriceComparisonService implements PriceComparisonService {
    @Override
    public List<UpcData> findLowestPriceAvailableUpcData(List<UpcData> upcDataList) {
        return upcDataList
                .stream()
                .filter(UpcData::isAvailable)
                .min(Comparator.comparingDouble(UpcData::getPrice)).stream().collect(Collectors.toList()); // todo same price wat doooo?
    }
}

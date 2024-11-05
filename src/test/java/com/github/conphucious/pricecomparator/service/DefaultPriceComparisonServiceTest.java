package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.model.UpcData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultPriceComparisonServiceTest {

    private PriceComparisonService defaultPriceComparisonService;

    private static final UpcData UPC_DATA_A = new UpcData(Merchant.builder().name("a").build(), 100, 5, true);
    private static final UpcData UPC_DATA_B = new UpcData(Merchant.builder().name("b").build(), 100, 1, false);
    private static final UpcData UPC_DATA_C = new UpcData(Merchant.builder().name("c").build(), 100, 5, true);
    private static final UpcData UPC_DATA_D = new UpcData(Merchant.builder().name("d").build(), 100, 9, true);

    @BeforeEach
    void setUp() {
        defaultPriceComparisonService = new DefaultPriceComparisonService();
    }

    @Test
    void findLowestPriceAvailableUpcDataFindsLowestPrice() {
        List<UpcData> upcDataList = List.of(UPC_DATA_A, UPC_DATA_D);
        List<UpcData> lowestPriceUpcDataList = defaultPriceComparisonService.findLowestPriceAvailableUpcData(upcDataList);

        assertEquals(1, lowestPriceUpcDataList.size());
        assertEquals(UPC_DATA_A, lowestPriceUpcDataList.get(0));
    }

    @Test
    void findLowestPriceAvailableUpcDataFindsLowestPrices() {
        List<UpcData> upcDataList = List.of(UPC_DATA_A, UPC_DATA_C, UPC_DATA_D);
        List<UpcData> lowestPriceUpcDataList = defaultPriceComparisonService.findLowestPriceAvailableUpcData(upcDataList);

        assertEquals(2, lowestPriceUpcDataList.size());
        assertTrue(lowestPriceUpcDataList.containsAll(List.of(UPC_DATA_A, UPC_DATA_C)));
    }

    @Test
    void findLowestPriceAvailableUpcDataFiltersAvailableItems() {
        List<UpcData> upcDataList = List.of(UPC_DATA_A, UPC_DATA_B, UPC_DATA_D);
        List<UpcData> lowestPriceUpcDataList = defaultPriceComparisonService.findLowestPriceAvailableUpcData(upcDataList);

        assertEquals(1, lowestPriceUpcDataList.size());
        assertEquals(UPC_DATA_A, lowestPriceUpcDataList.get(0));
    }

}

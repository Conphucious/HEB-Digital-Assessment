package com.github.conphucious.pricecomparator.controller;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.model.UpcData;
import com.github.conphucious.pricecomparator.service.MerchantService;
import com.github.conphucious.pricecomparator.service.PriceComparisonService;
import com.github.conphucious.pricecomparator.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PriceComparisonControllerTest {
    private PriceComparisonController priceComparisonController;
    private RequestService requestService;
    private MerchantService merchantService;
    private PriceComparisonService priceComparisonService;

    private static final int TEST_UPC = 1;

    @BeforeEach
    void setUp() {
        requestService = Mockito.mock(RequestService.class);
        merchantService = Mockito.mock(MerchantService.class);
        priceComparisonService = Mockito.mock(PriceComparisonService.class);
        priceComparisonController = new PriceComparisonController(requestService, merchantService, priceComparisonService);
    }

    @Test
    void determineUrlOfLowestUpcPriceWithAvailabilityAggregatesEndpoint() {
        String testEndpoint = "test";

        UpcData upcDataA = Mockito.mock(UpcData.class);

        when(requestService.requestMerchantData(TEST_UPC)).thenReturn(Map.of());
        when(merchantService.convertToUpcData(any(Map.class), any(Integer.class))).thenReturn(List.of());
        when(priceComparisonService.findLowestPriceAvailableUpcData(any())).thenReturn(List.of(upcDataA));
        when(upcDataA.getMerchant()).thenReturn(Merchant.builder().endpoint(testEndpoint).build());

        String urls = priceComparisonController.determineUrlOfLowestUpcPriceWithAvailability(TEST_UPC);
        assertFalse(urls.contains(","));
        assertEquals(testEndpoint, urls);
    }

    @Test
    void determineUrlOfLowestUpcPriceWithAvailabilityAggregatesEndpoints() {
        String testEndpointA = "testA";
        String testEndpointB = "testB";

        UpcData upcDataA = Mockito.mock(UpcData.class);
        UpcData upcDataB = Mockito.mock(UpcData.class);

        when(requestService.requestMerchantData(TEST_UPC)).thenReturn(Map.of());
        when(merchantService.convertToUpcData(any(Map.class), any(Integer.class))).thenReturn(List.of());
        when(priceComparisonService.findLowestPriceAvailableUpcData(any())).thenReturn(List.of(upcDataA, upcDataB));
        when(upcDataA.getMerchant()).thenReturn(Merchant.builder().endpoint(testEndpointA).build());
        when(upcDataB.getMerchant()).thenReturn(Merchant.builder().endpoint(testEndpointB).build());

        String urls = priceComparisonController.determineUrlOfLowestUpcPriceWithAvailability(TEST_UPC);
        assertTrue(urls.contains(","));
        assertTrue(urls.contains(testEndpointA));
        assertTrue(urls.contains(testEndpointB));
    }
}

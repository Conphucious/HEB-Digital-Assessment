package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultRequestServiceTest {

    private RequestService defaultRequestService;

    @BeforeEach
    void setUp() {
        defaultRequestService = new DefaultRequestService();
    }

    @Test
    void requestMerchantDataSucceeds() {
        Map<Merchant, HttpResponse<String>> merchantHttpResponseMap = defaultRequestService.requestMerchantData(101);
        assertEquals(3, merchantHttpResponseMap.keySet().size());
    }

    // Static call MerchantLoaderUtil.getMerchantData(); makes for harder testing. Can use power mockito on top.

}

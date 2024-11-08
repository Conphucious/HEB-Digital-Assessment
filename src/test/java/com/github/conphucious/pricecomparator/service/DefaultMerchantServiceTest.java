package com.github.conphucious.pricecomparator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.model.RegisteredMerchant;
import com.github.conphucious.pricecomparator.model.UpcData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class DefaultMerchantServiceTest {

    private MerchantService defaultMerchantService;

    private static final int TEST_UPC = 101;
    private static final Merchant TEST_MERCHANT_APPEDIA = Merchant.builder().name(RegisteredMerchant.APPEDIA).endpoint("a").build();
    private static final Merchant TEST_MERCHANT_MICROMAZON = Merchant.builder().name(RegisteredMerchant.MICROMAZON).endpoint("b").build();
    private static final Merchant TEST_MERCHANT_GOOGDIT = Merchant.builder().name(RegisteredMerchant.GOOGDIT).endpoint("c").build();
    private static final Merchant TEST_MERCHANT_UNREGISTERED_NAME = Merchant.builder().name("unregistered name").endpoint("url").build();

    private static final String TEST_MERCHANT_APPEDIA_HTTP_RESP_BODY = "{\"price\":\"$4.77\",\"stock\":7}";
    private static final String TEST_MERCHANT_MICROMAZON_HTTP_RESP_BODY = "{\"price\":5.67,\"available\":true}";
    private static final String TEST_MERCHANT_GOOGDIT_HTTP_RESP_BODY = "{\"p\":478000000,\"a\":[{\"l\":8839,\"q\":4},{\"l\":1292,\"q\":0}]}";

    @BeforeEach
    void setUp() {
        defaultMerchantService = new DefaultMerchantService();
    }

    @Test
    void convertToUpcDataSucceeds() {
        HttpResponse<String> httpResponseAppedia = Mockito.mock(HttpResponse.class);
        HttpResponse<String> httpResponseMicromazon = Mockito.mock(HttpResponse.class);
        HttpResponse<String> httpResponseGoogdit = Mockito.mock(HttpResponse.class);

        Map<Merchant, HttpResponse<String>> merchantHttpResponseMap = Map.of(
                TEST_MERCHANT_APPEDIA, httpResponseAppedia,
                TEST_MERCHANT_MICROMAZON, httpResponseMicromazon,
                TEST_MERCHANT_GOOGDIT, httpResponseGoogdit);

        when(httpResponseAppedia.body()).thenReturn(TEST_MERCHANT_APPEDIA_HTTP_RESP_BODY);
        when(httpResponseMicromazon.body()).thenReturn(TEST_MERCHANT_MICROMAZON_HTTP_RESP_BODY);
        when(httpResponseGoogdit.body()).thenReturn(TEST_MERCHANT_GOOGDIT_HTTP_RESP_BODY);

        when(httpResponseAppedia.statusCode()).thenReturn(200);
        when(httpResponseMicromazon.statusCode()).thenReturn(200);
        when(httpResponseGoogdit.statusCode()).thenReturn(200);

        List<UpcData> upcDataList = defaultMerchantService.convertToUpcData(merchantHttpResponseMap, TEST_UPC);
        assertEquals(3, upcDataList.size());

        // UPC
        assertEquals(TEST_UPC, upcDataList.get(0).getUpc());
        assertEquals(TEST_UPC, upcDataList.get(1).getUpc());
        assertEquals(TEST_UPC, upcDataList.get(2).getUpc());

        // Vendors match
        upcDataList.stream().forEach(m -> assertTrue(RegisteredMerchant.getMerchantNames().contains(m.getMerchant().getName())));
    }

    @Test
    void convertToUpcDataIllegalStateException() {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);

        Map<Merchant, HttpResponse<String>> merchantHttpResponseMap = Map.of(TEST_MERCHANT_UNREGISTERED_NAME, httpResponse);
        when(httpResponse.body()).thenReturn("test");
        when(httpResponse.statusCode()).thenReturn(200);

        assertThrows(IllegalStateException.class,
                () -> defaultMerchantService.convertToUpcData(merchantHttpResponseMap, TEST_UPC),
                "expected illegal state exception from merchant name mapping.");
    }

    @Test
    void convertToUpcDataReturns200StatusCodeDataOnly() {
        HttpResponse<String> httpResponseGoogdit = Mockito.mock(HttpResponse.class);
        HttpResponse<String> badHttpResponse = Mockito.mock(HttpResponse.class);

        // Map with two nulls
        Map<Merchant, HttpResponse<String>> merchantHttpResponseMap = new HashMap<>();
        merchantHttpResponseMap.put(TEST_MERCHANT_APPEDIA, badHttpResponse);
        merchantHttpResponseMap.put(TEST_MERCHANT_GOOGDIT, httpResponseGoogdit);

        when(badHttpResponse.body()).thenReturn(TEST_MERCHANT_APPEDIA_HTTP_RESP_BODY);
        when(badHttpResponse.statusCode()).thenReturn(500);
        when(httpResponseGoogdit.body()).thenReturn(TEST_MERCHANT_GOOGDIT_HTTP_RESP_BODY);
        when(httpResponseGoogdit.statusCode()).thenReturn(200);

        List<UpcData> upcDataList = defaultMerchantService.convertToUpcData(merchantHttpResponseMap, TEST_UPC);
        assertEquals(1, upcDataList.size());

        // UPC
        assertEquals(TEST_UPC, upcDataList.get(0).getUpc());

        // Vendor exists
        assertEquals(TEST_MERCHANT_GOOGDIT, upcDataList.get(0).getMerchant());
    }

    @Test
    void parseAppediaVendorUpcDataFieldsMatch() {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn(TEST_MERCHANT_APPEDIA_HTTP_RESP_BODY);

        try {
            UpcData upcData = defaultMerchantService.parseAppediaVendorUpcData(TEST_MERCHANT_APPEDIA, httpResponse, TEST_UPC);
            assertEquals(TEST_MERCHANT_APPEDIA, upcData.getMerchant());
            assertEquals(101, upcData.getUpc());
            assertEquals(4.77, upcData.getPrice());
            assertTrue(upcData.isAvailable());
        } catch (JsonProcessingException e) {
            fail();
        }
    }

    @Test
    void parseAppediaVendorUpcDataFails() {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn("{BAD JSON}");
        assertThrows(JsonProcessingException.class,
                () -> defaultMerchantService.parseAppediaVendorUpcData(TEST_MERCHANT_APPEDIA, httpResponse, TEST_UPC),
                "Expected json processing error for Appedia.");
    }

    @Test
    void parseMicromazonVendorUpcDataFieldsMatch() {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn(TEST_MERCHANT_MICROMAZON_HTTP_RESP_BODY);

        try {
            UpcData upcData = defaultMerchantService.parseMicromazonVendorUpcData(TEST_MERCHANT_MICROMAZON, httpResponse, TEST_UPC);
            assertEquals(TEST_MERCHANT_MICROMAZON, upcData.getMerchant());
            assertEquals(101, upcData.getUpc());
            assertEquals(5.67, upcData.getPrice());
            assertTrue(upcData.isAvailable());
        } catch (JsonProcessingException e) {
            fail();
        }
    }

    @Test
    void parseMicromazonVendorUpcDataFails() {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn("{BAD JSON}");
        assertThrows(JsonProcessingException.class,
                () -> defaultMerchantService.parseMicromazonVendorUpcData(TEST_MERCHANT_MICROMAZON, httpResponse, TEST_UPC),
                "Expected json processing error for Micromazon.");
    }

    @Test
    void parseGoogditVendorUpcDataFieldsMatch() {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn(TEST_MERCHANT_GOOGDIT_HTTP_RESP_BODY);

        try {
            UpcData upcData = defaultMerchantService.parseGoogditVendorUpcData(TEST_MERCHANT_GOOGDIT, httpResponse, TEST_UPC);
            assertEquals(TEST_MERCHANT_GOOGDIT, upcData.getMerchant());
            assertEquals(101, upcData.getUpc());
            assertEquals(4.78, upcData.getPrice());
            assertTrue(upcData.isAvailable());
        } catch (JsonProcessingException e) {
            fail();
        }
    }

    @Test
    void parseGoogditVendorUpcDataFails() {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn("{BAD JSON}");
        assertThrows(JsonProcessingException.class,
                () -> defaultMerchantService.parseGoogditVendorUpcData(TEST_MERCHANT_GOOGDIT, httpResponse, TEST_UPC),
                "Expected json processing error for Micromazon.");
    }

}

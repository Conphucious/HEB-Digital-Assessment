package com.github.conphucious.pricecomparator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.model.UpcData;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

/**
 * Handles all things involving Merchant data and related metadata.
 */
public interface MerchantService {
    /**
     * Converts map of Merchant http response mapped data into a list of UpcData.
     *
     * @param merchantHttpResponseMap
     * @param upc
     * @return List of Upc Data.
     */
    List<UpcData> convertToUpcData(Map<Merchant, HttpResponse<String>> merchantHttpResponseMap, int upc);

    /**
     * Parses Appedia vendor data. (#see dto.merchant.Appedia).
     *
     * @param merchant
     * @param httpResponse
     * @param upc
     * @return UpcData of Appedia vendor.
     * @throws JsonProcessingException
     */
    UpcData parseAppediaVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException;

    /**
     * Parses Micromazon vendor data. (#see dto.merchant.Micromazon).
     *
     * @param merchant
     * @param httpResponse
     * @param upc
     * @return UpcData of Micromazon vendor.
     * @throws JsonProcessingException
     */
    UpcData parseMicromazonVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException;

    /**
     * Parses Googdit vendor data. (#see dto.merchant.Googdit).
     *
     * @param merchant
     * @param httpResponse
     * @param upc
     * @return UpcData of Googdit vendor.
     * @throws JsonProcessingException
     */
    UpcData parseGoogditVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException;
}

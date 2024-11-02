package com.github.conphucious.pricecomparator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.model.UPCData;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public interface MerchantService {
    List<UPCData> convertToDto(Map<Merchant, HttpResponse<String>> merchantHttpResponseMap, int upc);

    UPCData parseAppediaVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException;
    UPCData parseMicromazonVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException;
    UPCData parseGoogditVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException;
}

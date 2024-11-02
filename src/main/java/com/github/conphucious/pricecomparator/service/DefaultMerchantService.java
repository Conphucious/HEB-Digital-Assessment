package com.github.conphucious.pricecomparator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.conphucious.pricecomparator.dto.merchant.Appedia;
import com.github.conphucious.pricecomparator.dto.merchant.Googdit;
import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.dto.merchant.MerchantName;
import com.github.conphucious.pricecomparator.dto.merchant.Micromazon;
import com.github.conphucious.pricecomparator.model.UPCData;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultMerchantService implements MerchantService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<UPCData> convertToDto(Map<Merchant, HttpResponse<String>> merchantHttpResponseMap, int upc) {

        Set<Merchant> validMerchants = merchantHttpResponseMap.keySet()
                .stream()
                .filter(key -> merchantHttpResponseMap.get(key) != null)
                .collect(Collectors.toSet());

        return merchantHttpResponseMap.entrySet()
                .stream()
                .filter(entrySet -> entrySet.getValue() != null) // non null values
                .map(entrySet -> mapMerchantToUpcData(entrySet.getKey(), entrySet.getValue(), upc))
                .collect(Collectors.toList());
    }

    @Override
    public UPCData parseAppediaVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException {
        Appedia appedia = objectMapper.readValue(httpResponse.body(), Appedia.class);
        Double price = Double.valueOf(appedia.getPrice().replace("$", ""));
        boolean isAvailable = appedia.getStock() > 1;
        return new UPCData(merchant, upc, price, isAvailable);
    }

    @Override
    public UPCData parseMicromazonVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException {
        Micromazon micromazon = objectMapper.readValue(httpResponse.body(), Micromazon.class);
        return new UPCData(merchant, upc, micromazon.getPrice(), micromazon.getAvailable());
    }

    @Override
    public UPCData parseGoogditVendorUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) throws JsonProcessingException {
        Googdit googdit = objectMapper.readValue(httpResponse.body(), Googdit.class);
        Double price = Double.valueOf(googdit.getP() * 1000000); // microcents
        boolean isAvailable = !Arrays.asList(googdit.getA()).isEmpty();
        return new UPCData(merchant, upc, price, isAvailable);
    }

    private UPCData mapMerchantToUpcData(Merchant merchant, HttpResponse<String> httpResponse, int upc) {
        // determine which vendor

        try {
            if (merchant.getName().equalsIgnoreCase(MerchantName.APPEDIA)) {
                return parseAppediaVendorUpcData(merchant, httpResponse, upc);
            } else if (merchant.getName().equalsIgnoreCase(MerchantName.MICROMAZON)) {
                return parseMicromazonVendorUpcData(merchant, httpResponse, upc);
            } else if (merchant.getName().equalsIgnoreCase(MerchantName.GOOGDIT)) {
                return parseGoogditVendorUpcData(merchant, httpResponse, upc);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("An error occurred when converting response JSON to DTO DefaultMerchantService#mapMerchantToUpcData, " + e.getMessage());
        }

        throw new IllegalStateException("No applicable merchant found when converting " + merchant.getName() + " to a DTO in DefaultMerchantService#mapMerchantToUpcData.");
    }

}

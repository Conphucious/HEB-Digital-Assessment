package com.github.conphucious.pricecomparator.controller;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.model.UPCData;
import com.github.conphucious.pricecomparator.service.DefaultMerchantService;
import com.github.conphucious.pricecomparator.service.DefaultPriceComparisonService;
import com.github.conphucious.pricecomparator.service.DefaultRequestService;
import com.github.conphucious.pricecomparator.service.MerchantService;
import com.github.conphucious.pricecomparator.service.PriceComparisonService;
import com.github.conphucious.pricecomparator.service.RequestService;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PriceComparisonController {
    private final RequestService requestService;
    private final MerchantService merchantService;
    private final PriceComparisonService priceComparisonService;

    public PriceComparisonController() {
        requestService = new DefaultRequestService();
        merchantService = new DefaultMerchantService();
        priceComparisonService = new DefaultPriceComparisonService();
    }

    public Optional<String> determineUrlOfLowestUpcPriceWithAvailability(int upc) {
        Map<Merchant, HttpResponse<String>> merchantHttpResponseMap = requestService.requestMerchantData(upc);
        List<UPCData> upcDataList = merchantService.convertToDto(merchantHttpResponseMap, upc);
        Optional<UPCData> lowestPriceUpcData = priceComparisonService.findLowestPriceAvailableUpcData(upcDataList);

        return lowestPriceUpcData.isPresent()
                ? Optional.of(lowestPriceUpcData.get().getMerchant().getEndpoint())
                : Optional.empty();
    }

}

package com.github.conphucious.pricecomparator.controller;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.model.UpcData;
import com.github.conphucious.pricecomparator.service.DefaultMerchantService;
import com.github.conphucious.pricecomparator.service.DefaultPriceComparisonService;
import com.github.conphucious.pricecomparator.service.DefaultRequestService;
import com.github.conphucious.pricecomparator.service.MerchantService;
import com.github.conphucious.pricecomparator.service.PriceComparisonService;
import com.github.conphucious.pricecomparator.service.RequestService;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for Price Comparison. If Spring was used, this would be an endpoint with injected DIs for service classes.
 * Opting to not create an interface as there is no obvious need for extensibility.
 */
public class PriceComparisonController {
    private final RequestService requestService;
    private final MerchantService merchantService;
    private final PriceComparisonService priceComparisonService;

    public PriceComparisonController(RequestService requestService,
                                     MerchantService merchantService,
                                     PriceComparisonService priceComparisonService) {
        this.requestService = requestService;
        this.merchantService = merchantService;
        this.priceComparisonService = priceComparisonService;
    }


    public String determineUrlOfLowestUpcPriceWithAvailability(int upc) {
        Map<Merchant, HttpResponse<String>> merchantHttpResponseMap = requestService.requestMerchantData(upc);
        List<UpcData> upcDataList = merchantService.convertToUpcData(merchantHttpResponseMap, upc);
        List<UpcData> lowestPriceUpcData = priceComparisonService.findLowestPriceAvailableUpcData(upcDataList);
        return lowestPriceUpcData.stream().map(data -> data.getMerchant().getEndpoint()).collect(Collectors.joining(", "));
    }

}

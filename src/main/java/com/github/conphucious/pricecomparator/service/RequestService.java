package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.model.UPCData;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

/**
 * Request service interface which defines the HTTP request calls for a given UPC.
 */
public interface RequestService {

    /**
     * Make an HTTP request to UPC data from specified merchants (#see MerchantUrlLoaderUtil class).
     * Only includes successful responses, not unsuccessful responses.
     * @param upc - universal product code
     * @return Map of merchants to successful http response objects
     */
    Map<Merchant, HttpResponse<String>> requestMerchantData(int upc);
}

package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.model.UPCData;

import java.util.List;

/**
 * Request service interface which defines the HTTP request calls for a given UPC.
 */
public interface RequestService {

    /**
     * Make an HTTP request to UPC data from specified merchants (#see MerchantUrlLoaderUtil class)
     * @param upc - universal product code
     * @return Response list of UPCData as a result of the HTTP calls.
     */
    List<UPCData> requestMerchantUpcData(int upc);
}

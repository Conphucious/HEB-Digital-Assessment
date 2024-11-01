package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.model.UPCData;

import java.util.List;

/**
 * Request service interface which defines the HTTP request calls for a given UPC.
 */
public interface RequestService {
    List<UPCData> requestUpcData(int upc);
}

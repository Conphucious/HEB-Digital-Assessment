package com.github.conphucious.pricecomparator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * POJO model representing the price, availability, and quantity (if applicable through availability) of a UPC.
 */

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UPCData {
    /*
        We will ignore the true data structure of a universal product code which may be better suited as an array
        since there are defined chunk partitions and assume a `upc` will only be an integer value for this assessment.
     */
    private final int upc;
    private final String merchantUrl;
    private final double price;
    private final boolean isAvailable;

    // Only exists on isAvailable clause
    private Optional<Integer> quantityAvailable;
}

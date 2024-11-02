package com.github.conphucious.pricecomparator.model;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

/**
 * POJO model representing the price, availability, and quantity (if applicable through availability) of a UPC.
 */

@ToString
@Getter
@RequiredArgsConstructor
public class UPCData {
    private final Merchant merchant;
    /*
        We will ignore the true data structure of a universal product code which may be better suited as an array
        since there are defined chunk partitions and assume a `upc` will only be an integer value for this assessment.
     */
    private final int upc;
    private final double price;
    private final boolean isAvailable;
}

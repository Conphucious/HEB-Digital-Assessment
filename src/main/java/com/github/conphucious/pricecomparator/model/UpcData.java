package com.github.conphucious.pricecomparator.model;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * POJO model representing the price and availability of a given UPC per merchant.
 */

@ToString
@Getter
@RequiredArgsConstructor
public class UpcData {
    private final Merchant merchant;
    /*
        We will ignore the true data structure of a universal product code which may be better suited as an array
        since there are defined chunk partitions and assume a `upc` will only be an integer value for this assessment.
     */
    private final int upc;
    private final double price;
    private final boolean isAvailable;
}

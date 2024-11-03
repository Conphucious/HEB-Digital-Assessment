package com.github.conphucious.pricecomparator.dto.merchant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for Appedia merchant http response data from Jackson Object Mapper.
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Appedia {
    private String price;
    private Integer stock;
}

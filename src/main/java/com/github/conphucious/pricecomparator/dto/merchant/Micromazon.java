package com.github.conphucious.pricecomparator.dto.merchant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for Micromazon merchant http response data from Jackson Object Mapper.
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Micromazon {
    private Double price;
    private Boolean available;
}

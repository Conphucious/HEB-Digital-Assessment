package com.github.conphucious.pricecomparator.dto.merchant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    private String name;
    @Setter
    private String endpoint;
}

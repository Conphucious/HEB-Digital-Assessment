package com.github.conphucious.pricecomparator.dto.merchant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * General DTO used for converting Merchant json data into a DTO. (#see MerchantLoaderUtil).
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    private String name;
    @Setter // Used to inject endpoint with upc.
    private String endpoint; // alternatively if multiple endpoints exist, can refactor to upcEndpoint
}

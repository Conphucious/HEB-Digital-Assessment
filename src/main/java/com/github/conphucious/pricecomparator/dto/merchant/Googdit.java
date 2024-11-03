package com.github.conphucious.pricecomparator.dto.merchant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * DTO for Googdit merchant http response data from Jackson Object Mapper.
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Googdit {
    private Availability[] a; // availability
    private Long p; // Value is represented in micro-cents.

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Availability {
        private int l; // location
        private int q; // quantity
    }
}

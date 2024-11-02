package com.github.conphucious.pricecomparator.dto.merchant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Googdit {
    private Availability[] a; // availability
    private Long p;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Availability {
        private int l; // location
        private int q; // quantity
    }
}

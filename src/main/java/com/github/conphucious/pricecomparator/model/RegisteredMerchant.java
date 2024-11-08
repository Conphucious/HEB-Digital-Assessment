package com.github.conphucious.pricecomparator.model;

import java.util.List;

/**
 * Definitions of Merchant names. Must match the name in classpath > resources/config/merchant.json. Used to assist in
 * converting HTTP response data from vendors into a merchant vendor DTO. (#see service.MerchantService)
 */
public class RegisteredMerchant {
    private RegisteredMerchant() {
    }

    public static final String APPEDIA = "appedia";
    public static final String MICROMAZON = "micromazon";
    public static final String GOOGDIT = "googdit";

    public static List<String> getMerchantNames() {
        return List.of(APPEDIA, MICROMAZON, GOOGDIT);
    }
}

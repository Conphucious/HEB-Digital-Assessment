package com.github.conphucious.pricecomparator.dto.merchant;

/**
 * Definitions of Merchant names. Must match the name in classpath > resources/config/merchant.json. Used to assist in
 * converting HTTP response data from vendors into a merchant vendor DTO. (#see service.MerchantService)
 */
public class MerchantName {
    private MerchantName() {
    }

    public static final String APPEDIA = "appedia";
    public static final String MICROMAZON = "micromazon";
    public static final String GOOGDIT = "googdit";
}

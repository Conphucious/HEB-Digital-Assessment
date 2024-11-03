package com.github.conphucious.pricecomparator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class MerchantLoaderUtil {

    /**
     * Converts merchant data from classpath > resources/config/merchant.json into Merchant DTOs.
     *
     * @return List of Merchants
     */
    public static List<Merchant> getMerchantData() {
        try {
            URL configUrl = getConfigurationPath();
            ObjectMapper objectMapper = new ObjectMapper();
            return Arrays.asList(objectMapper.readValue(new File(configUrl.getPath()), Merchant[].class));
        } catch (IOException e) {
            System.out.println("An error has occurred reading in the path of merchants.json: " + e.getMessage()); // Log error
        }

        throw new IllegalStateException("Illegal state when retrieving merchants via util.MerchantLoaderUtil#getMerchantData"); // log.error
    }

    // Can alternatively be a class on its own definition like MerchantName
    protected URL getConfigurationPath() {
        return MerchantLoaderUtil.class.getClassLoader().getResource("config/merchant.json");
    }

}

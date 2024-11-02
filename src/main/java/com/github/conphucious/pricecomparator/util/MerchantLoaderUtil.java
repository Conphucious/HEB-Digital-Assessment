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

    public static List<Merchant> getMerchants() {
        try {
            URL configUrl = getConfigurationPath();
            ObjectMapper objectMapper = new ObjectMapper();
            return Arrays.asList(objectMapper.readValue(new File(configUrl.getPath()), Merchant[].class));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error has occurred reading in the path of merchants.json: " + e.getMessage()); // Log error
        }

        throw new IllegalStateException("Illegal state when retrieving merchants.");
    }

    private URL getConfigurationPath() {
        return MerchantLoaderUtil.class.getClassLoader().getResource("config/merchant.json");
    }

}

package com.github.conphucious.pricecomparator.util;

import lombok.experimental.UtilityClass;

import java.net.URL;
import java.util.List;

@UtilityClass
public class MerchantUrlLoaderUtil {

    public static List<String> getMerchantUrls() {
        return null;
    }

    private URL getConfigurationPath() {
        return MerchantUrlLoaderUtil.class.getClassLoader().getResource("config/merchant.json");
    }

}

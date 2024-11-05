package com.github.conphucious.pricecomparator.util;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.dto.merchant.RegisteredMerchant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MerchantLoaderUtilTest {

    // Can also test for IllegalStateException but these should inherently fail the below test.
    @Test
    void getMerchantDataReturnsSuccessfullyMappedMerchantDto() {
        List<Merchant> merchants = MerchantLoaderUtil.getMerchantData();
        int size = merchants.size();

        assertEquals(3, size);
        assertEquals(merchants.get(0).getName(), RegisteredMerchant.APPEDIA);
        assertEquals(merchants.get(1).getName(), RegisteredMerchant.MICROMAZON);
        assertEquals(merchants.get(2).getName(), RegisteredMerchant.GOOGDIT);

        assertFalse(merchants.get(0).getEndpoint().isEmpty());
        assertFalse(merchants.get(1).getEndpoint().isEmpty());
        assertFalse(merchants.get(2).getEndpoint().isEmpty());
    }


    @Test
    void getConfigurationPathReturnsSuccessfulPath() {
        URL url = MerchantLoaderUtil.getConfigurationPath();
        File file = new File(url.getPath());
        assertTrue(file.exists());
    }

}

package com.github.conphucious.pricecomparator;

import com.github.conphucious.pricecomparator.service.DefaultRequestService;
import com.github.conphucious.pricecomparator.util.MerchantLoaderUtil;

import java.net.URISyntaxException;

public class Application {
    public static void main(String[] args) throws URISyntaxException {
        System.out.println(MerchantLoaderUtil.getMerchants());
        DefaultRequestService d = new DefaultRequestService();
        d.requestMerchantUpcData(101);
    }
}

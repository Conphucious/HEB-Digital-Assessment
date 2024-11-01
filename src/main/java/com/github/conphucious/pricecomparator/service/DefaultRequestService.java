package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.dto.Merchant;
import com.github.conphucious.pricecomparator.model.UPCData;
import com.github.conphucious.pricecomparator.util.MerchantLoaderUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class DefaultRequestService implements RequestService {

    private final HttpClient httpClient;
    private final static String PLACEHOLDER_VALUE_KEY = "PLACEHOLDER_VALUE";

    public DefaultRequestService() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Override
    public List<UPCData> requestMerchantUpcData(int upc) {
        List<Merchant> merchants = MerchantLoaderUtil.getMerchants();

        for (Merchant merchant : merchants) {
            String endpoint = merchant.getEndpoint().replace(PLACEHOLDER_VALUE_KEY, String.valueOf(upc));
            getRequestMerchant(endpoint);
        }
        return null;
    }

    private UPCData getRequestMerchant(String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

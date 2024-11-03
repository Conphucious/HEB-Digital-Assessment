package com.github.conphucious.pricecomparator.service;

import com.github.conphucious.pricecomparator.dto.merchant.Merchant;
import com.github.conphucious.pricecomparator.util.MerchantLoaderUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultRequestService implements RequestService {

    private final HttpClient httpClient;
    private final static String PLACEHOLDER_VALUE_KEY = "PLACEHOLDER_VALUE"; // Used to inject UPC into endpoint URI.

    public DefaultRequestService() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Override
    public Map<Merchant, HttpResponse<String>> requestMerchantData(int upc) {
        List<Merchant> merchants = MerchantLoaderUtil.getMerchantData();
        return merchants
                .stream()
                .collect(
                        Collectors.toMap(
                                merchant -> merchant,
                                merchant -> getRequestMerchant(merchant, upc).orElse(null))
                        // Alternatively can return optional instead of allowing null value.
                );
    }

    private Optional<HttpResponse<String>> getRequestMerchant(Merchant merchant, int upc) {
        // Inject UPC into Endpoint URI. Can alternatively make a service which cleans/etls this data or MerchantLoaderUtil.
        String endpoint = merchant.getEndpoint().replace(PLACEHOLDER_VALUE_KEY, String.valueOf(upc));

        // mutate original endpoint <-- not ideal in workflow. See above comment
        merchant.setEndpoint(endpoint);

        // HTTP Request
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(endpoint))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Requests for merchant '" + merchant.getName() + "' was: " + response.statusCode()); // log.debug
                return Optional.of(response);
            } else {
                System.out.println("An error occurred making an HTTP GET request to '" + merchant.getName() + "'. with code: " + response.statusCode()); // log.warn
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred making an HTTP GET request to '" + merchant.getName() + "'. with code"); // log.warn
        }

        return Optional.empty();
    }
}

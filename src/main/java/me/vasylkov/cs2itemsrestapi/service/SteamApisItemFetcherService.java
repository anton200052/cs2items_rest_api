package me.vasylkov.cs2itemsrestapi.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.model.Item;
import me.vasylkov.cs2itemsrestapi.model.SteamApisItemsResponse;
import me.vasylkov.cs2itemsrestapi.exception.NullBodyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SteamApisItemFetcherService implements ItemFetcherService {
    private final RestTemplate restTemplate;

    @Value("${webapi.api_key}")
    private String apiKey;

    @Override
    public List<Item> fetchItems() {
        String allItemsURL = "https://api.steamapis.com/market/items/730?api_key=" + apiKey;

        ResponseEntity<SteamApisItemsResponse> response = restTemplate.exchange(allItemsURL, HttpMethod.GET, null, SteamApisItemsResponse.class);

        if (response.getBody() == null || response.getBody().getData() == null) {
            throw new NullBodyException("Received null items body");
        }

        return new ArrayList<>(response.getBody().getData());
    }
}

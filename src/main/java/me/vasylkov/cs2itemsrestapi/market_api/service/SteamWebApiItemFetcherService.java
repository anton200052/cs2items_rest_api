package me.vasylkov.cs2itemsrestapi.market_api.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiItem;
import me.vasylkov.cs2itemsrestapi.rest.exception.NullBodyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SteamWebApiItemFetcherService implements ItemFetcherService
{
    private final RestTemplate restTemplate;

    @Value("${itemsapi.all_items_api_url}")
    private String allItemsURL;

    @Override
    public List<Item> fetchItems() {
        ResponseEntity<List<SteamWebApiItem>> response = restTemplate.exchange(
                allItemsURL,
                HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {}
        );

        if (response.getBody() == null)
        {
            throw new NullBodyException("Received null items body");
        }

        return new ArrayList<>(response.getBody());
    }
}

package me.vasylkov.cs2itemsrestapi.cs_backpack_api.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.cs_backpack_api.entity.ApiResponseItemList;
import me.vasylkov.cs2itemsrestapi.cs_backpack_api.entity.ApiResponseItemSingle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ItemFetcherService
{
    private final RestTemplate restTemplate;

    @Value("${itemsapi.all_items_api_url}")
    private String allItemsURL;

    @Value("${itemsapi.single_item_api_url}")
    private String singleItemUrl;

    public ApiResponseItemList fetchItems()
    {
        return restTemplate.getForObject(allItemsURL, ApiResponseItemList.class);
    }

    public ApiResponseItemSingle fetchSingleItem(String name, CurrencyCode currencyCode)
    {
        String urlTemplate = singleItemUrl + "?id=" + name + "&currency=" + currencyCode;
        return restTemplate.getForObject(urlTemplate, ApiResponseItemSingle.class);
    }
}

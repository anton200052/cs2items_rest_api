package me.vasylkov.cs2itemsrestapi.market_api.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiCurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiItem;
import me.vasylkov.cs2itemsrestapi.rest.exception.NullBodyException;
import me.vasylkov.cs2itemsrestapi.resttemplate.response_entity.SteamWebApiCurrencyRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamWebApiCurrencyFetcherService implements CurrencyFetcherService
{
    private final RestTemplate restTemplate;

    @Value("${itemsapi.currency_rates_api_url}")
    private String currencyUrl;

    @Override
    public List<CurrencyRate> fetchAllCurrencyRates(CurrencyCode base)
    {
        String url = UriComponentsBuilder.fromHttpUrl(currencyUrl).queryParam("base", base.toString()).toUriString();

        SteamWebApiCurrencyRateResponse currencyRateResponse = restTemplate.getForObject(url, SteamWebApiCurrencyRateResponse.class);
        if (currencyRateResponse == null || currencyRateResponse.getRates() == null)
        {
            throw new NullBodyException("Received null rates body");
        }
        return currencyRateResponse.getRates().entrySet().stream().map(entry -> new SteamWebApiCurrencyRate(entry.getValue(), currencyRateResponse.getBase(), entry.getKey())).collect(Collectors.toList());
    }

}

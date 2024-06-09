package me.vasylkov.cs2itemsrestapi.market_api.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiCurrencyRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SteamWebApiCurrencyFetcherService implements CurrencyFetcherService
{
    private final RestTemplate restTemplate;

    @Value("${itemsapi.currency_rates_api_url}")
    private String currencyUrl;

    @Override
    public CurrencyRate fetchCurrencyRate(CurrencyCode base, CurrencyCode change)
    {
        String url = UriComponentsBuilder.fromHttpUrl(currencyUrl)
                .queryParam("base", base)
                .queryParam("change", change)
                .toUriString();
        return restTemplate.getForObject(url, SteamWebApiCurrencyRate.class);
    }

    @Override
    public List<CurrencyRate> fetchAllCurrencyRates()
    {
        List<CurrencyRate> currencyRates = new ArrayList<>();
        for (CurrencyCode currencyCode : CurrencyCode.values())
        {
            currencyRates.add(fetchCurrencyRate(CurrencyCode.USD, currencyCode));
        }
        return currencyRates;
    }

}

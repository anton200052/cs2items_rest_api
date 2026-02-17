package me.vasylkov.cs2itemsrestapi.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.model.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.exception.NullBodyException;
import me.vasylkov.cs2itemsrestapi.model.CurrencyRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamInvHelperCurrencyFetcherService implements CurrencyFetcherService {
    private final RestTemplate restTemplate;

    @Override
    public List<CurrencyRate> fetchAllCurrencyRates(CurrencyCode base) {
        String currencyListUrl = "https://api.steaminventoryhelper.com/steam-rates";
        String url = UriComponentsBuilder.fromUriString(currencyListUrl).queryParam("base", base.toString()).toUriString();

        CurrencyRateResponse currencyRateResponse = restTemplate.getForObject(url, CurrencyRateResponse.class);
        if (currencyRateResponse == null || currencyRateResponse.getRates() == null) {
            throw new NullBodyException("Received null rates body");
        }
        return currencyRateResponse.getRates().entrySet().stream().map(entry -> new CurrencyRate(entry.getValue(), base, entry.getKey())).collect(Collectors.toList());
    }

}


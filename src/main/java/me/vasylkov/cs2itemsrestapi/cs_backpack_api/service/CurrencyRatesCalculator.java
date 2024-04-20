package me.vasylkov.cs2itemsrestapi.cs_backpack_api.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyRatesCalculator
{
    private final ItemFetcherService itemFetcherService;

    @Value("${itemsapi.item_name_for_updating_currency_rates}")
    private String itemNameForUpdatingCurrencyRates;

    public List<CurrencyRate> getCurrencyRates()
    {
        List<CurrencyRate> currencyRates = new ArrayList<>();
        for (CurrencyCode currencyCode : CurrencyCode.values())
        {
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setCurrencyCode(currencyCode);
            currencyRate.setRate(calculateCurrencyRate(currencyCode));
            currencyRate.setLastUpdated(LocalDateTime.now());

            currencyRates.add(currencyRate);
        }
        return currencyRates;
    }

    private double calculateCurrencyRate(CurrencyCode currencyCode)
    {
        double usdAveragePrice = itemFetcherService.fetchSingleItem(itemNameForUpdatingCurrencyRates, CurrencyCode.USD).getPrice().getAverage();
        double currencyCodeAveragePrice = itemFetcherService.fetchSingleItem(itemNameForUpdatingCurrencyRates, currencyCode).getPrice().getAverage();

        return currencyCodeAveragePrice / usdAveragePrice;
    }
}

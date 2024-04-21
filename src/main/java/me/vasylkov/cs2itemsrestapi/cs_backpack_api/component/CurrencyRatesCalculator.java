package me.vasylkov.cs2itemsrestapi.cs_backpack_api.component;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.cs_backpack_api.service.ItemFetcherService;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
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

    private BigDecimal calculateCurrencyRate(CurrencyCode currencyCode)
    {
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        BigDecimal usdAveragePrice = itemFetcherService.fetchSingleItem(itemNameForUpdatingCurrencyRates, CurrencyCode.USD).getPrice().getAverage();
        BigDecimal currencyCodeAveragePrice = itemFetcherService.fetchSingleItem(itemNameForUpdatingCurrencyRates, currencyCode).getPrice().getAverage();
        return currencyCodeAveragePrice.divide(usdAveragePrice, 2, RoundingMode.HALF_UP);
    }
}

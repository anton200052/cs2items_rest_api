package me.vasylkov.cs2itemsrestapi.rest.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.Price;
import me.vasylkov.cs2itemsrestapi.database.service.CurrencyRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConversionService
{
    private final CurrencyRateService currencyRateService;

    public void convertPrice(Price price, CurrencyCode currencyCode)
    {
        if (currencyCode != CurrencyCode.USD)
        {
            CurrencyRate currencyRate = currencyRateService.findCurrencyRateByCurrencyCode(currencyCode);
            BigDecimal rate = currencyRate.getRate();

            price.setAverage(multiplyAndRound(price.getAverage(), rate));
            price.setMedian(multiplyAndRound(price.getMedian(), rate));
            price.setStandardDeviation(multiplyAndRound(price.getStandardDeviation(), rate));
            price.setLowestPrice(multiplyAndRound(price.getLowestPrice(), rate));
            price.setHighestPrice(multiplyAndRound(price.getHighestPrice(), rate));
        }
    }

    private BigDecimal multiplyAndRound(BigDecimal value, BigDecimal rate)
    {
        return value.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}

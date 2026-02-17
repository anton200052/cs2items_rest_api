package me.vasylkov.cs2itemsrestapi.component;

import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.model.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.model.Item;
import me.vasylkov.cs2itemsrestapi.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PriceConverterByCurrencyRate
{
    @Qualifier("steamApisCurrencyService")
    private final CurrencyRateService currencyService;

    public PriceConverterByCurrencyRate(CurrencyRateService currencyService)
    {
        this.currencyService = currencyService;
    }

    public void convertPrice(Item item, CurrencyCode changeCode) {
        CurrencyRate changeRate = currencyService.findCurrencyRateByCurrencyCode(changeCode);
        BigDecimal conversionRate = changeRate.getChangeRate();

        if (item.getPrices() != null) {
            Item.Prices prices = item.getPrices();
            prices.setLatest(convert(prices.getLatest(), conversionRate));
            prices.setSafe(convert(prices.getSafe(), conversionRate));
            prices.setMedian(convert(prices.getMedian(), conversionRate));
            prices.setAvg(convert(prices.getAvg(), conversionRate));
            prices.setMin(convert(prices.getMin(), conversionRate));
            prices.setMax(convert(prices.getMax(), conversionRate));
        }
    }

    private BigDecimal convert(BigDecimal price, BigDecimal conversionRate) {
        if (price == null) {
            return null;
        }
        return price.multiply(conversionRate).setScale(2, RoundingMode.HALF_UP);
    }
}

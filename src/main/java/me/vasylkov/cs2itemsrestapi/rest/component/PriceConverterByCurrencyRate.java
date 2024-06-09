package me.vasylkov.cs2itemsrestapi.rest.component;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PriceConverterByCurrencyRate
{
    @Qualifier("steamWebApiCurrencyService")
    private final CurrencyRateService currencyService;

    public PriceConverterByCurrencyRate(CurrencyRateService currencyService)
    {
        this.currencyService = currencyService;
    }

    public void convertPrice(Item item, CurrencyCode changeCode) {
        CurrencyRate changeRate = currencyService.findCurrencyRateByCurrencyCode(changeCode);

        BigDecimal conversionRate = changeRate.getChangeRate();

        item.setPriceLatest(convert(item.getPriceLatest(), conversionRate));
        item.setPriceReal(convert(item.getPriceReal(), conversionRate));
        item.setPriceMedian(convert(item.getPriceMedian(), conversionRate));
        item.setPriceAvg(convert(item.getPriceAvg(), conversionRate));
        item.setPriceMin(convert(item.getPriceMin(), conversionRate));
        item.setPriceMax(convert(item.getPriceMax(), conversionRate));
    }

    private BigDecimal convert(BigDecimal price, BigDecimal conversionRate) {
        if (price == null) {
            return null;
        }
        return price.multiply(conversionRate).setScale(2, RoundingMode.HALF_UP);
    }
}

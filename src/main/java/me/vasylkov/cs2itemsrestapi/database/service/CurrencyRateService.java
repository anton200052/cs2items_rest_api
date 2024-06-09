package me.vasylkov.cs2itemsrestapi.database.service;

import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiCurrencyRate;

import java.util.List;

public interface CurrencyRateService
{
    void updateCurrencyRates(List<CurrencyRate> currencyRates);
    void saveAllCurrencyRates(List<CurrencyRate> currencyRates);
    CurrencyRate findCurrencyRateById(long id);
    CurrencyRate findCurrencyRateByCurrencyCode(CurrencyCode currencyCode);
    List<CurrencyRate> findAllCurrencyRates();
}

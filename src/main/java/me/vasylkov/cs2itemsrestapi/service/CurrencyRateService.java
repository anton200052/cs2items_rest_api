package me.vasylkov.cs2itemsrestapi.service;

import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateService
{
    void updateCurrencyRates(List<CurrencyRate> currencyRates);
    void saveAllCurrencyRates(List<CurrencyRate> currencyRates);
    CurrencyRate findCurrencyRateById(long id);
    CurrencyRate findCurrencyRateByCurrencyCode(CurrencyCode currencyCode);
    List<CurrencyRate> findAllCurrencyRates();
}

package me.vasylkov.cs2itemsrestapi.market_api.service;

import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;

import java.util.List;

public interface CurrencyFetcherService
{
    CurrencyRate fetchCurrencyRate(CurrencyCode base, CurrencyCode change);
    List<CurrencyRate> fetchAllCurrencyRates();
}

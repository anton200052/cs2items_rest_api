package me.vasylkov.cs2itemsrestapi.service;

import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.model.CurrencyRate;

import java.util.List;

public interface CurrencyFetcherService {
    List<CurrencyRate> fetchAllCurrencyRates(CurrencyCode base);
}

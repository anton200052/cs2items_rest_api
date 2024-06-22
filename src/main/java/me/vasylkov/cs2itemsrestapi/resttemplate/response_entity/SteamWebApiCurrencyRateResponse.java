package me.vasylkov.cs2itemsrestapi.resttemplate.response_entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
public class SteamWebApiCurrencyRateResponse
{
    private CurrencyCode base;
    private Map<CurrencyCode, BigDecimal> rates;
}

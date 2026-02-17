package me.vasylkov.cs2itemsrestapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
public class CurrencyRateResponse
{
    private Map<CurrencyCode, BigDecimal> rates;
}

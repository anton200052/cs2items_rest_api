package me.vasylkov.cs2itemsrestapi.dto;

import lombok.Data;
import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;

import java.math.BigDecimal;

@Data
public class CurrencyRateDTO
{
    private CurrencyCode base;

    private CurrencyCode change;

    private BigDecimal changeRate;
}

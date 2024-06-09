package me.vasylkov.cs2itemsrestapi.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO
{
    private String marketHashName;

    private BigDecimal priceLatest;

    private BigDecimal priceReal;

    private BigDecimal priceMedian;

    private BigDecimal priceAvg;

    private BigDecimal priceMin;

    private BigDecimal priceMax;
}

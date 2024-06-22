package me.vasylkov.cs2itemsrestapi.database.entity;

import java.math.BigDecimal;

public interface CurrencyRate
{
    BigDecimal getChangeRate();

    void setChangeRate(BigDecimal changeRate);

    CurrencyCode getChange();

    void setChange(CurrencyCode change);

    CurrencyCode getBase();

    void setBase(CurrencyCode base);
}

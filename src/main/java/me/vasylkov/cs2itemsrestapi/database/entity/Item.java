package me.vasylkov.cs2itemsrestapi.database.entity;

import java.math.BigDecimal;

public interface Item {

    String getMarketHashName();
    void setMarketHashName(String marketHashName);

    BigDecimal getPriceLatest();
    void setPriceLatest(BigDecimal priceLatest);

    BigDecimal getPriceReal();
    void setPriceReal(BigDecimal priceReal);

    BigDecimal getPriceMedian();
    void setPriceMedian(BigDecimal priceMedian);

    BigDecimal getPriceAvg();
    void setPriceAvg(BigDecimal priceAvg);

    BigDecimal getPriceMin();
    void setPriceMin(BigDecimal priceMin);

    BigDecimal getPriceMax();
    void setPriceMax(BigDecimal priceMax);
}


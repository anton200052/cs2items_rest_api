package me.vasylkov.cs2itemsrestapi.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "market_items")  // Updated to match the SQL table name
public class SteamWebApiItem implements Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "markethashname")
    @JsonProperty("markethashname")
    private String marketHashName;

    @Column(name = "pricelatest")
    @JsonProperty("pricelatest")
    private BigDecimal priceLatest;

    @Column(name = "pricereal")
    @JsonProperty("pricereal")
    private BigDecimal priceReal;

    @Column(name = "pricemedian")
    @JsonProperty("pricemedian")
    private BigDecimal priceMedian;

    @Column(name = "priceavg")
    @JsonProperty("priceavg")
    private BigDecimal priceAvg;

    @Column(name = "pricemin")
    @JsonProperty("pricemin")
    private BigDecimal priceMin;

    @Column(name = "pricemax")
    @JsonProperty("pricemax")
    private BigDecimal priceMax;

    private static BigDecimal scale(BigDecimal value)
    {
        return value == null ? null : value.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SteamWebApiItem that = (SteamWebApiItem) o;
        return Objects.equals(marketHashName, that.marketHashName) &&
                (priceReal == null ? that.priceReal == null : scale(priceReal).compareTo(scale(that.priceReal)) == 0) &&
                (priceMedian == null ? that.priceMedian == null : scale(priceMedian).compareTo(scale(that.priceMedian)) == 0) &&
                (priceAvg == null ? that.priceAvg == null : scale(priceAvg).compareTo(scale(that.priceAvg)) == 0) &&
                (priceMin == null ? that.priceMin == null : scale(priceMin).compareTo(scale(that.priceMin)) == 0) &&
                (priceMax == null ? that.priceMax == null : scale(priceMax).compareTo(scale(that.priceMax)) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketHashName, priceLatest, priceReal, priceMedian, priceAvg, priceMin, priceMax);
    }
}


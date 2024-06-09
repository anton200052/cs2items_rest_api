package me.vasylkov.cs2itemsrestapi.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SteamWebApiItem steamWebApiItem = (SteamWebApiItem) o;
        return
                Objects.equals(marketHashName, steamWebApiItem.marketHashName) &&
                (priceReal == null ? steamWebApiItem.priceReal == null : priceReal.compareTo(steamWebApiItem.priceReal) == 0) &&
                (priceMedian == null ? steamWebApiItem.priceMedian == null : priceMedian.compareTo(steamWebApiItem.priceMedian) == 0) &&
                (priceAvg == null ? steamWebApiItem.priceAvg == null : priceAvg.compareTo(steamWebApiItem.priceAvg) == 0) &&
                (priceMin == null ? steamWebApiItem.priceMin == null : priceMin.compareTo(steamWebApiItem.priceMin) == 0) &&
                (priceMax == null ? steamWebApiItem.priceMax == null : priceMax.compareTo(steamWebApiItem.priceMax) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketHashName, priceLatest, priceReal, priceMedian, priceAvg, priceMin, priceMax);
    }
}


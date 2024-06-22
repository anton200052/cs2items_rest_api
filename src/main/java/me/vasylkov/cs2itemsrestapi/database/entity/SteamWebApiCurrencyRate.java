package me.vasylkov.cs2itemsrestapi.database.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "currency_rates")
@NoArgsConstructor
public class SteamWebApiCurrencyRate implements CurrencyRate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "change_rate", nullable = false)
    private BigDecimal changeRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "base_code", nullable = false, length = 3)
    private CurrencyCode base;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_code", nullable = false, length = 3)
    private CurrencyCode change;

    public SteamWebApiCurrencyRate(BigDecimal changeRate, CurrencyCode base, CurrencyCode change)
    {
        this.changeRate = changeRate;
        this.base = base;
        this.change = change;
    }

    private BigDecimal scale(BigDecimal value)
    {
        return value == null ? null : value.setScale(6, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SteamWebApiCurrencyRate that = (SteamWebApiCurrencyRate) o;
        return (changeRate == null ? that.changeRate == null : scale(changeRate).compareTo(scale(that.changeRate)) == 0) &&
                base == that.base &&
                change == that.change;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(changeRate, base, change);
    }
}

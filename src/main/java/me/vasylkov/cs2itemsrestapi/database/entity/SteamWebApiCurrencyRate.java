package me.vasylkov.cs2itemsrestapi.database.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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

    @Column(name = "base_rate", nullable = false)
    private BigDecimal baseRate;

    @Column(name = "change_rate", nullable = false)
    private BigDecimal changeRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "base_code", nullable = false, length = 3)
    private CurrencyCode base;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_code", nullable = false, length = 3)
    private CurrencyCode change;

    @Column(name = "symbol", nullable = false)
    private String symbol;


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SteamWebApiCurrencyRate that = (SteamWebApiCurrencyRate) o;
        return
                (baseRate == null ? that.baseRate == null : baseRate.compareTo(that.baseRate) == 0) &&
                (changeRate == null ? that.changeRate == null : changeRate.compareTo(that.changeRate) == 0) &&
                base == that.base &&
                change == that.change &&
                Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(baseRate, changeRate, base, change, symbol);
    }
}

package me.vasylkov.cs2itemsrestapi.database.entity;


import jakarta.persistence.*;
import lombok.Data;
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
public class CurrencyRate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", nullable = false, length = 3)
    private CurrencyCode currencyCode;;

    @Column(name = "rate", nullable = false)
    private double rate;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return Double.compare(that.rate, rate) == 0 &&
                currencyCode == that.currencyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyCode, rate, lastUpdated);
    }
}

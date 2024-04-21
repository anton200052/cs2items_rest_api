package me.vasylkov.cs2itemsrestapi.database.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Column(name="average")
    @JsonAlias({"average", "average_price"})
    private BigDecimal average;

    @Column(name="median")
    @JsonAlias({"median", "median_price"})
    private BigDecimal median;

    @Column(name="sold")
    @JsonAlias({"sold", "amount_sold"})
    private int sold;

    @Column(name="standard_deviation")
    @JsonProperty("standard_deviation")
    private BigDecimal standardDeviation;

    @Column(name="lowest_price")
    @JsonProperty("lowest_price")
    private BigDecimal lowestPrice;

    @Column(name="highest_price")
    @JsonProperty("highest_price")
    private BigDecimal highestPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return sold == price.sold &&
                (average == null ? price.average == null : average.compareTo(price.average) == 0) &&
                (median == null ? price.median == null : median.compareTo(price.median) == 0) &&
                (standardDeviation == null ? price.standardDeviation == null : standardDeviation.compareTo(price.standardDeviation) == 0) &&
                (lowestPrice == null ? price.lowestPrice == null : lowestPrice.compareTo(price.lowestPrice) == 0) &&
                (highestPrice == null ? price.highestPrice == null : highestPrice.compareTo(price.highestPrice) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, average, median, sold, standardDeviation, lowestPrice, highestPrice);
    }
}


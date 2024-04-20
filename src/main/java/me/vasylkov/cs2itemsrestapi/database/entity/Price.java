package me.vasylkov.cs2itemsrestapi.database.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private double average;

    @Column(name="median")
    @JsonAlias({"median", "median_price"})
    private double median;

    @Column(name="sold")
    @JsonAlias({"sold", "amount_sold"})
    private int sold;

    @Column(name="standard_deviation")
    @JsonProperty("standard_deviation")
    private double standardDeviation;

    @Column(name="lowest_price")
    @JsonProperty("lowest_price")
    private double lowestPrice;

    @Column(name="highest_price")
    @JsonProperty("highest_price")
    private double highestPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(price.average, average) == 0 &&
                Double.compare(price.median, median) == 0 &&
                sold == price.sold &&
                Double.compare(price.standardDeviation, standardDeviation) == 0 &&
                Double.compare(price.lowestPrice, lowestPrice) == 0 &&
                Double.compare(price.highestPrice, highestPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, average, median, sold, standardDeviation, lowestPrice, highestPrice);
    }
}


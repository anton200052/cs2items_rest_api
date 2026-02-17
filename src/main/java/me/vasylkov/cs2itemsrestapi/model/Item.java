package me.vasylkov.cs2itemsrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "market_items")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "markethashname")
    @JsonProperty("market_hash_name")
    private String marketHashName;

    @Column(name = "marketname")
    @JsonProperty("market_name")
    private String marketName;

    @Column(name = "name_id")
    @JsonProperty("nameID")
    private String nameId;

    @Column(name = "image")
    @JsonProperty("image")
    private String image;

    @Column(name = "border_color")
    @JsonProperty("border_color")
    private String borderColor;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Long updatedAt;

    @Embedded
    @JsonProperty("prices")
    private Prices prices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(marketHashName, item.marketHashName) &&
                Objects.equals(nameId, item.nameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketHashName, nameId);
    }

    @Data
    @Embeddable
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Prices {
        @Column(name = "pricelatest")
        @JsonProperty("latest")
        private BigDecimal latest;

        @Column(name = "pricemin")
        @JsonProperty("min")
        private BigDecimal min;

        @Column(name = "pricemax")
        @JsonProperty("max")
        private BigDecimal max;

        @Column(name = "priceavg")
        @JsonProperty("avg")
        private BigDecimal avg;

        @Column(name = "pricemean")
        @JsonProperty("mean")
        private BigDecimal mean;

        @Column(name = "pricemedian")
        @JsonProperty("median")
        private BigDecimal median;

        @Column(name = "pricereal")
        @JsonProperty("safe")
        private BigDecimal safe;

        @Embedded
        @AttributeOverrides({
            @AttributeOverride(name = "last24h", column = @Column(name = "safe_ts_last_24h")),
            @AttributeOverride(name = "last7d", column = @Column(name = "safe_ts_last_7d")),
            @AttributeOverride(name = "last30d", column = @Column(name = "safe_ts_last_30d")),
            @AttributeOverride(name = "last90d", column = @Column(name = "safe_ts_last_90d"))
        })
        @JsonProperty("safe_ts")
        private SafeTs safeTs;

        @Embedded
        @AttributeOverrides({
            @AttributeOverride(name = "last24h", column = @Column(name = "sold_last_24h")),
            @AttributeOverride(name = "last7d", column = @Column(name = "sold_last_7d")),
            @AttributeOverride(name = "last30d", column = @Column(name = "sold_last_30d")),
            @AttributeOverride(name = "last90d", column = @Column(name = "sold_last_90d")),
            @AttributeOverride(name = "avgDailyVolume", column = @Column(name = "sold_avg_daily_volume"))
        })
        @JsonProperty("sold")
        private Sold sold;

        @Column(name = "unstable")
        @JsonProperty("unstable")
        private Boolean unstable;

        @Column(name = "unstable_reason")
        @JsonProperty("unstable_reason")
        private String unstableReason;

        @Column(name = "first_seen")
        @JsonProperty("first_seen")
        private Long firstSeen;
    }

    @Data
    @Embeddable
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SafeTs {
        @JsonProperty("last_24h")
        private BigDecimal last24h;
        @JsonProperty("last_7d")
        private BigDecimal last7d;
        @JsonProperty("last_30d")
        private BigDecimal last30d;
        @JsonProperty("last_90d")
        private BigDecimal last90d;
    }

    @Data
    @Embeddable
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sold {
        @JsonProperty("last_24h")
        private Integer last24h;
        @JsonProperty("last_7d")
        private Integer last7d;
        @JsonProperty("last_30d")
        private Integer last30d;
        @JsonProperty("last_90d")
        private Integer last90d;
        @JsonProperty("avg_daily_volume")
        private Integer avgDailyVolume;
    }
}

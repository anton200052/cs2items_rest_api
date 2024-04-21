package me.vasylkov.cs2itemsrestapi.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.vasylkov.cs2itemsrestapi.cs_backpack_api.mapping.ItemPriceDeserializer;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "marketable")
    private Boolean marketable;

    @Column(name = "tradable")
    private Boolean tradable;

    @Column(name = "class_id")
    @JsonProperty("classid")
    private String classId;

    @Column(name = "type")
    private String type;

    @Column(name = "rarity")
    private String rarity;

    @Column(name = "rarity_color")
    @JsonProperty("rarity_color")
    private String rarityColor;

    @Column(name = "first_sale_date")
    @JsonProperty("first_sale_date")
    private Long firstSaleDate;

    @JsonDeserialize(using = ItemPriceDeserializer.class)
    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Price price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(marketable, item.marketable) &&
                Objects.equals(tradable, item.tradable) &&
                Objects.equals(name, item.name) &&
                Objects.equals(classId, item.classId) &&
                Objects.equals(type, item.type) &&
                Objects.equals(rarity, item.rarity) &&
                Objects.equals(rarityColor, item.rarityColor) &&
                Objects.equals(firstSaleDate, item.firstSaleDate) &&
                price.equals(item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, marketable, tradable, classId, type, rarity, rarityColor, firstSaleDate, price);
    }
}

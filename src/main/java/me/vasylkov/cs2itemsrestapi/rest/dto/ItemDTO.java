package me.vasylkov.cs2itemsrestapi.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.vasylkov.cs2itemsrestapi.database.entity.Price;

@Data
public class ItemDTO
{
    private String name;

    private boolean marketable;

    private boolean tradable;

    private String classId;

    private String type;

    private String rarity;

    @JsonProperty("rarity_color")
    private String rarityColor;

    @JsonProperty("first_sale_date")
    private Long firstSaleDate;

    private Price price;
}

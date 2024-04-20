package me.vasylkov.cs2itemsrestapi.cs_backpack_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.cs_backpack_api.mapping.ItemListDeserializer;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiResponseItemList extends ApiResponseItem
{
    @JsonDeserialize(using = ItemListDeserializer.class)
    @JsonProperty("items_list")
    private List<Item> items;
}

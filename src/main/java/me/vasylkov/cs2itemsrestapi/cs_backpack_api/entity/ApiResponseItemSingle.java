package me.vasylkov.cs2itemsrestapi.cs_backpack_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.vasylkov.cs2itemsrestapi.database.entity.Price;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiResponseItemSingle extends ApiResponseItem
{
    @JsonUnwrapped
    private Price price;
}


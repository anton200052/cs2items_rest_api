package me.vasylkov.cs2itemsrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SteamApisItemsResponse {
    @JsonProperty("appID")
    private Integer appId;

    @JsonProperty("data")
    private List<Item> data;
}

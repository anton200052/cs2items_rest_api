package me.vasylkov.cs2itemsrestapi.cs_backpack_api.entity;

import lombok.Data;

@Data
public abstract class ApiResponseItem
{
    private boolean success;
    private String currency;
}


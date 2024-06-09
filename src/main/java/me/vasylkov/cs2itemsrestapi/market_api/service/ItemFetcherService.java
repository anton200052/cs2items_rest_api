package me.vasylkov.cs2itemsrestapi.market_api.service;

import me.vasylkov.cs2itemsrestapi.database.entity.Item;

import java.util.List;

public interface ItemFetcherService
{
    List<Item> fetchItems();
}

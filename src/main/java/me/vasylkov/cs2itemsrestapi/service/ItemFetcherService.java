package me.vasylkov.cs2itemsrestapi.service;

import me.vasylkov.cs2itemsrestapi.model.Item;

import java.util.List;

public interface ItemFetcherService
{
    List<Item> fetchItems();
}

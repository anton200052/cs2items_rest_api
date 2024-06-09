package me.vasylkov.cs2itemsrestapi.database.service;

import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiItem;

import java.util.List;

public interface ItemService
{
    void updateItems(List<Item> steamWebApiItems);
    void saveAllItems(List<Item> steamWebApiItems);
    Item findItemById(int id);
    Item findItemByName(String name);
    List<Item> findAllItems();
}

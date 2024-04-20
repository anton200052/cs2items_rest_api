package me.vasylkov.cs2itemsrestapi.database.service;

import me.vasylkov.cs2itemsrestapi.database.entity.Item;

import java.util.List;

public interface ItemService
{
    void updateItems(List<Item> items);

    void saveAllItems(List<Item> items);
    Item findItemById(int id);
    Item findItemByName(String name);
    List<Item> findAllItems();
}

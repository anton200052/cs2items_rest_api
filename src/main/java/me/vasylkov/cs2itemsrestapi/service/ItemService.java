package me.vasylkov.cs2itemsrestapi.service;

import me.vasylkov.cs2itemsrestapi.model.Item;

import java.util.List;

public interface ItemService {
    void updateItems(List<Item> steamApisItems);

    void saveAllItems(List<Item> steamApisItems);

    Item findItemById(int id);

    Item findItemByName(String name);

    List<Item> findAllItems();
}


package me.vasylkov.cs2itemsrestapi.database.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.dao.ItemRepository;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.entity.Price;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImp implements ItemService
{
    private final ItemRepository itemRepository;

    @Transactional
    public void updateItems(List<Item> items)
    {
        Map<String, Item> existingItems = findAllItems().stream().collect(Collectors.toMap(Item::getName, Function.identity()));

        List<Item> toBeSaved = new ArrayList<>();
        int updatedCount = 0;
        for (Item apiItem : items)
        {
            Item dbItem = existingItems.getOrDefault(apiItem.getName(), new Item());
            if (!dbItem.equals(apiItem))
            {
                dbItem.setName(apiItem.getName());
                dbItem.setMarketable(apiItem.isMarketable());
                dbItem.setTradable(apiItem.isTradable());
                dbItem.setClassId(apiItem.getClassId());
                dbItem.setIconUrl(apiItem.getIconUrl());
                dbItem.setIconUrlLarge(apiItem.getIconUrlLarge());
                dbItem.setType(apiItem.getType());
                dbItem.setRarity(apiItem.getRarity());
                dbItem.setRarityColor(apiItem.getRarityColor());
                dbItem.setFirstSaleDate(apiItem.getFirstSaleDate());

                Price apiItemPrice = apiItem.getPrice();
                Price dbItemPrice = dbItem.getPrice();

                if (dbItemPrice != null)
                {
                    apiItemPrice.setId(dbItem.getPrice().getId());
                }
                apiItemPrice.setItem(dbItem);

                dbItem.setPrice(apiItemPrice);
                toBeSaved.add(dbItem);
                updatedCount++;
            }
        }

        if (!toBeSaved.isEmpty())
        {
            saveAllItems(toBeSaved);
        }

        System.out.println("Updated " + updatedCount + " items.");
    }

    @Override
    public void saveAllItems(List<Item> items)
    {
        itemRepository.saveAll(items);
    }

    @Override
    public Item findItemById(int id)
    {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public Item findItemByName(String name)
    {
        return itemRepository.findByName(name).orElse(null);
    }

    @Override
    public List<Item> findAllItems()
    {
        return itemRepository.findAll();
    }
}

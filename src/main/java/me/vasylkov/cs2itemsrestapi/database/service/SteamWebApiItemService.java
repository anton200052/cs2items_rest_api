package me.vasylkov.cs2itemsrestapi.database.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.dao.SteamWebApiItemRepository;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiItem;
import me.vasylkov.cs2itemsrestapi.rest.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamWebApiItemService implements ItemService
{
    private final SteamWebApiItemRepository steamWebApiItemRepository;
    private final Logger logger;

    @Override
    @Transactional
    public void updateItems(List<Item> steamWebApiItems)
    {
        Map<String, Item> existingItems = findAllItems().stream().collect(Collectors.toMap(Item::getMarketHashName, Function.identity()));

        List<Item> toBeSaved = new ArrayList<>();
        int updatedCount = 0;
        for (Item apiSteamWebApiItem : steamWebApiItems)
        {
            Item dbSteamWebApiItem = existingItems.getOrDefault(apiSteamWebApiItem.getMarketHashName(), new SteamWebApiItem());
            if (!dbSteamWebApiItem.equals(apiSteamWebApiItem))
            {
                dbSteamWebApiItem.setMarketHashName(apiSteamWebApiItem.getMarketHashName());
                dbSteamWebApiItem.setPriceLatest(apiSteamWebApiItem.getPriceLatest());
                dbSteamWebApiItem.setPriceReal(apiSteamWebApiItem.getPriceReal());
                dbSteamWebApiItem.setPriceMedian(apiSteamWebApiItem.getPriceMedian());
                dbSteamWebApiItem.setPriceAvg(apiSteamWebApiItem.getPriceAvg());
                dbSteamWebApiItem.setPriceMin(apiSteamWebApiItem.getPriceMin());
                dbSteamWebApiItem.setPriceMax(apiSteamWebApiItem.getPriceMax());

                toBeSaved.add(dbSteamWebApiItem);
                updatedCount++;
            }
        }

        if (!toBeSaved.isEmpty())
        {
            saveAllItems(toBeSaved);
        }

        logger.info("Updated {} items.", updatedCount);
    }

    @Override
    @Transactional
    public void saveAllItems(List<Item> steamWebApiItems)
    {
        List<SteamWebApiItem> steamWebApiItemList = steamWebApiItems.stream()
                .map(item -> (SteamWebApiItem) item)
                .collect(Collectors.toList());
        steamWebApiItemRepository.saveAll(steamWebApiItemList);
    }

    @Override
    @Transactional
    public Item findItemById(int id)
    {
        return steamWebApiItemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Item findItemByName(String name)
    {
        String changedName = name.replaceAll("'", "&#39");
        return steamWebApiItemRepository.findByMarketHashName(name).orElseThrow(() -> new EntityNotFoundException("Item with name " + changedName + " not found"));
    }

    @Override
    @Transactional
    public List<Item> findAllItems()
    {
        return new ArrayList<>(steamWebApiItemRepository.findAll());
    }
}

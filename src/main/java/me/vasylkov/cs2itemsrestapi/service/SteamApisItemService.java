package me.vasylkov.cs2itemsrestapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.repository.SteamApisItemRepository;
import me.vasylkov.cs2itemsrestapi.model.Item;
import me.vasylkov.cs2itemsrestapi.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamApisItemService implements ItemService {
    private final SteamApisItemRepository steamApisItemRepository;
    private final Logger logger;

    @Override
    @Transactional
    public void updateItems(List<Item> steamApisItems) {
        Map<String, Item> existingItems = findAllItems().stream().collect(Collectors.toMap(Item::getMarketHashName, Function.identity(), (existing, replacement) -> replacement));

        List<Item> toBeSaved = new ArrayList<>();
        int updatedCount = 0;
        for (Item apiSteamApisItem : steamApisItems) {
            Item dbSteamApisItem = existingItems.getOrDefault(apiSteamApisItem.getMarketHashName(), new Item());
            
            dbSteamApisItem.setMarketHashName(apiSteamApisItem.getMarketHashName());
            dbSteamApisItem.setMarketName(apiSteamApisItem.getMarketName());
            dbSteamApisItem.setNameId(apiSteamApisItem.getNameId());
            dbSteamApisItem.setImage(apiSteamApisItem.getImage());
            dbSteamApisItem.setBorderColor(apiSteamApisItem.getBorderColor());
            dbSteamApisItem.setUpdatedAt(apiSteamApisItem.getUpdatedAt());
            
            updatePrices(dbSteamApisItem, apiSteamApisItem.getPrices());

            toBeSaved.add(dbSteamApisItem);
            updatedCount++;
        }

        if (!toBeSaved.isEmpty()) {
            saveAllItems(toBeSaved);
        }

        logger.info("Updated {} items.", updatedCount);
    }

    private void updatePrices(Item dbItem, Item.Prices newPrices) {
        if (newPrices == null) {
            dbItem.setPrices(null);
            return;
        }

        if (dbItem.getPrices() == null) {
            dbItem.setPrices(new Item.Prices());
        }

        Item.Prices dbPrices = dbItem.getPrices();
        dbPrices.setLatest(newPrices.getLatest());
        dbPrices.setMin(newPrices.getMin());
        dbPrices.setMax(newPrices.getMax());
        dbPrices.setAvg(newPrices.getAvg());
        dbPrices.setMean(newPrices.getMean());
        dbPrices.setMedian(newPrices.getMedian());
        dbPrices.setSafe(newPrices.getSafe());
        dbPrices.setUnstable(newPrices.getUnstable());
        dbPrices.setUnstableReason(newPrices.getUnstableReason());
        dbPrices.setFirstSeen(newPrices.getFirstSeen());

        // Update Sold
        if (newPrices.getSold() != null) {
            if (dbPrices.getSold() == null) {
                dbPrices.setSold(new Item.Sold());
            }
            Item.Sold dbSold = dbPrices.getSold();
            Item.Sold newSold = newPrices.getSold();
            dbSold.setLast24h(newSold.getLast24h());
            dbSold.setLast7d(newSold.getLast7d());
            dbSold.setLast30d(newSold.getLast30d());
            dbSold.setLast90d(newSold.getLast90d());
            dbSold.setAvgDailyVolume(newSold.getAvgDailyVolume());
        } else {
            dbPrices.setSold(null);
        }

        // Update SafeTs
        if (newPrices.getSafeTs() != null) {
            if (dbPrices.getSafeTs() == null) {
                dbPrices.setSafeTs(new Item.SafeTs());
            }
            Item.SafeTs dbSafeTs = dbPrices.getSafeTs();
            Item.SafeTs newSafeTs = newPrices.getSafeTs();
            dbSafeTs.setLast24h(newSafeTs.getLast24h());
            dbSafeTs.setLast7d(newSafeTs.getLast7d());
            dbSafeTs.setLast30d(newSafeTs.getLast30d());
            dbSafeTs.setLast90d(newSafeTs.getLast90d());
        } else {
            dbPrices.setSafeTs(null);
        }
    }

    @Override
    @Transactional
    public void saveAllItems(List<Item> steamApisItems) {
        List<Item> itemList = steamApisItems.stream().map(item -> (Item) item).collect(Collectors.toList());
        steamApisItemRepository.saveAll(itemList);
    }

    @Override
    @Transactional
    public Item findItemById(int id) {
        return steamApisItemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Item findItemByName(String name) {
        String changedName = name.replaceAll("'", "&#39");
        return steamApisItemRepository.findByMarketHashName(name).orElseThrow(() -> new EntityNotFoundException("Item with name " + changedName + " not found"));
    }

    @Override
    @Transactional
    public List<Item> findAllItems() {
        return new ArrayList<>(steamApisItemRepository.findAll());
    }
}

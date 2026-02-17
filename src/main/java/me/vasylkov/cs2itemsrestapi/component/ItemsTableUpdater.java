package me.vasylkov.cs2itemsrestapi.component;

import me.vasylkov.cs2itemsrestapi.service.ItemService;
import me.vasylkov.cs2itemsrestapi.service.ItemFetcherService;
import me.vasylkov.cs2itemsrestapi.service.SteamApisItemFetcherService;
import me.vasylkov.cs2itemsrestapi.exception.DatabaseUpdateUnexpectedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ItemsTableUpdater implements TableUpdater
{
    @Qualifier("steamApisItemService")
    private final ItemService itemService;
    @Qualifier("steamApisItemFetcherService")
    private final ItemFetcherService itemFetcherService;
    private final DbUpdateStatus dbUpdateStatus;
    private final Logger logger;

    public ItemsTableUpdater(ItemService itemService, SteamApisItemFetcherService itemFetcherService, DbUpdateStatus dbUpdateStatus, Logger logger)
    {
        this.itemService = itemService;
        this.itemFetcherService = itemFetcherService;
        this.dbUpdateStatus = dbUpdateStatus;
        this.logger = logger;
    }

    @Override
    public void updateTable()
    {
        try
        {
            logger.info("Updating items DB");
            dbUpdateStatus.setUpdating(true);
            itemService.updateItems(itemFetcherService.fetchItems());
        }
        catch (Exception e)
        {
            logger.error("Произошла ошибка при обновлении таблицы итемов: {}", e.getMessage());
            throw new DatabaseUpdateUnexpectedException("Ошибка обновления таблицы итемов, дополнительная информация доступна в консоли");
        }
        finally
        {
            dbUpdateStatus.setUpdating(false);
        }
    }
}


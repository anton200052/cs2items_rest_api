package me.vasylkov.cs2itemsrestapi.db_updater.component;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiCurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiItem;
import me.vasylkov.cs2itemsrestapi.database.service.CurrencyRateService;
import me.vasylkov.cs2itemsrestapi.database.service.ItemService;
import me.vasylkov.cs2itemsrestapi.market_api.service.ItemFetcherService;
import me.vasylkov.cs2itemsrestapi.market_api.service.SteamWebApiItemFetcherService;
import me.vasylkov.cs2itemsrestapi.rest.exception.DatabaseUpdateUnexpectedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SteamWebApiItemsUpdater implements TableUpdater
{
    @Qualifier("steamWebApiItemService")
    private final ItemService itemService;
    @Qualifier("steamWebApiItemFetcherService")
    private final ItemFetcherService itemFetcherService;
    private final DbUpdateStatus dbUpdateStatus;
    private final Logger logger;

    public SteamWebApiItemsUpdater(ItemService itemService, SteamWebApiItemFetcherService itemFetcherService, DbUpdateStatus dbUpdateStatus, Logger logger)
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

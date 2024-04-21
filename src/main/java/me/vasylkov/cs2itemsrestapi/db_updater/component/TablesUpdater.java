package me.vasylkov.cs2itemsrestapi.db_updater.component;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.cs_backpack_api.component.CurrencyRatesCalculator;
import me.vasylkov.cs2itemsrestapi.database.service.CurrencyRateService;
import me.vasylkov.cs2itemsrestapi.database.service.ItemService;
import me.vasylkov.cs2itemsrestapi.cs_backpack_api.service.ItemFetcherService;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TablesUpdater implements DatabaseTablesUpdater
{
    private final ItemService itemService;
    private final ItemFetcherService itemFetcherService;
    private final CurrencyRatesCalculator currencyRatesCalculator;
    private final CurrencyRateService currencyRateService;
    private final DbUpdateStatus dbUpdateStatus;
    private final Logger logger;

    @Async
    @Override
    public void updateTables()
    {
        try
        {
            logger.info("Updating tables");
            dbUpdateStatus.setUpdating(true);
            currencyRateService.updateCurrencyRates(currencyRatesCalculator.getCurrencyRates());
            itemService.updateItems(itemFetcherService.fetchItems().getItems());
        }
        finally
        {
            dbUpdateStatus.setUpdating(false);
        }
    }
}

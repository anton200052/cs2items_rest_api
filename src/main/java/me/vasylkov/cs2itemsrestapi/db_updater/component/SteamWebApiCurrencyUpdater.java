package me.vasylkov.cs2itemsrestapi.db_updater.component;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiCurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.service.CurrencyRateService;
import me.vasylkov.cs2itemsrestapi.database.service.ItemService;
import me.vasylkov.cs2itemsrestapi.market_api.service.CurrencyFetcherService;
import me.vasylkov.cs2itemsrestapi.market_api.service.SteamWebApiCurrencyFetcherService;
import me.vasylkov.cs2itemsrestapi.market_api.service.SteamWebApiItemFetcherService;
import me.vasylkov.cs2itemsrestapi.rest.exception.DatabaseUpdateUnexpectedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SteamWebApiCurrencyUpdater implements TableUpdater
{
    @Qualifier("steamWebApiCurrencyFetcherService")
    private final CurrencyFetcherService currencyFetcherService;
    @Qualifier("steamWebApiCurrencyService")
    private final CurrencyRateService currencyRateService;
    private final DbUpdateStatus dbUpdateStatus;
    private final Logger logger;

    @Value("${itemsapi.base_currency_rate}")
    private CurrencyCode base;

    public SteamWebApiCurrencyUpdater(SteamWebApiCurrencyFetcherService currencyFetcherService, CurrencyRateService currencyRateService, DbUpdateStatus dbUpdateStatus, Logger logger)
    {
        this.currencyFetcherService = currencyFetcherService;
        this.currencyRateService = currencyRateService;
        this.dbUpdateStatus = dbUpdateStatus;
        this.logger = logger;
    }

    @Override
    public void updateTable()
    {
        try
        {
            logger.info("Updating currency DB");
            dbUpdateStatus.setUpdating(true);
            currencyRateService.updateCurrencyRates(currencyFetcherService.fetchAllCurrencyRates(base));
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

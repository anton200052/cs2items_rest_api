package me.vasylkov.cs2itemsrestapi.component;

import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.service.CurrencyRateService;
import me.vasylkov.cs2itemsrestapi.service.CurrencyFetcherService;
import me.vasylkov.cs2itemsrestapi.service.SteamInvHelperCurrencyFetcherService;
import me.vasylkov.cs2itemsrestapi.exception.DatabaseUpdateUnexpectedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrencyTableUpdater implements TableUpdater
{
    @Qualifier("steamInvHelperCurrencyFetcherService")
    private final CurrencyFetcherService currencyFetcherService;
    @Qualifier("steamInvHelperCurrencyService")
    private final CurrencyRateService currencyRateService;
    private final DbUpdateStatus dbUpdateStatus;
    private final Logger logger;

    @Value("${webapi.base_currency_rate}")
    private CurrencyCode base;

    public CurrencyTableUpdater(SteamInvHelperCurrencyFetcherService currencyFetcherService, CurrencyRateService currencyRateService, DbUpdateStatus dbUpdateStatus, Logger logger)
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


package me.vasylkov.cs2itemsrestapi.rest.controller;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiCurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.service.CurrencyRateService;
import me.vasylkov.cs2itemsrestapi.database.service.SteamWebApiCurrencyService;
import me.vasylkov.cs2itemsrestapi.db_updater.component.DbUpdateChecker;
import me.vasylkov.cs2itemsrestapi.db_updater.component.SteamWebApiCurrencyUpdater;
import me.vasylkov.cs2itemsrestapi.db_updater.component.TableUpdater;
import me.vasylkov.cs2itemsrestapi.rest.dto.CurrencyRateDTO;
import me.vasylkov.cs2itemsrestapi.rest.component.EntityDTOConverter;
import me.vasylkov.cs2itemsrestapi.rest.component.ResponseEntityConverter;
import me.vasylkov.cs2itemsrestapi.rest.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currencyRates")
public class CurrencyRateController
{
    @Qualifier("steamWebApiCurrencyService")
    private final CurrencyRateService rateService;
    private final EntityDTOConverter<CurrencyRate, CurrencyRateDTO> entityDTOConverter;
    private final DbUpdateChecker dbUpdateChecker;
    private final ResponseEntityConverter<CurrencyRateDTO> converter;
    private final ResponseEntityConverter<MessageDTO> messageConverter;
    @Qualifier("steamWebApiCurrencyUpdater")
    private final TableUpdater currencyUpdater;

    public CurrencyRateController(CurrencyRateService rateService, EntityDTOConverter<CurrencyRate, CurrencyRateDTO> entityDTOConverter, DbUpdateChecker dbUpdateChecker, ResponseEntityConverter<CurrencyRateDTO> converter, ResponseEntityConverter<MessageDTO> messageConverter, SteamWebApiCurrencyUpdater currencyUpdater)
    {
        this.rateService = rateService;
        this.entityDTOConverter = entityDTOConverter;
        this.dbUpdateChecker = dbUpdateChecker;
        this.converter = converter;
        this.messageConverter = messageConverter;
        this.currencyUpdater = currencyUpdater;
    }

    @GetMapping("/singleRate")
    public ResponseEntity<?> getCurrencyRate(@RequestParam(name = "currencyCode", required = false, defaultValue = "USD") CurrencyCode currencyCode)
    {
        dbUpdateChecker.throwIfUpdating();
        CurrencyRate currencyRate = rateService.findCurrencyRateByCurrencyCode(currencyCode);
        CurrencyRateDTO currencyRateDTO = entityDTOConverter.convertToDto(currencyRate);

        return converter.convertToResponseEntity(currencyRateDTO, HttpStatus.OK);
    }

    @GetMapping("/updateRates")
    public ResponseEntity<?> updateRates()
    {
        dbUpdateChecker.throwIfUpdating();
        currencyUpdater.updateTable();
        return messageConverter.convertToResponseEntity(new MessageDTO("Success"), HttpStatus.OK);
    }
}

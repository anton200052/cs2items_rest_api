package me.vasylkov.cs2itemsrestapi.rest.controller;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.service.CurrencyRateService;
import me.vasylkov.cs2itemsrestapi.database.service.ItemService;
import me.vasylkov.cs2itemsrestapi.db_updater.component.DbUpdateChecker;
import me.vasylkov.cs2itemsrestapi.rest.dto.CurrencyRateDTO;
import me.vasylkov.cs2itemsrestapi.rest.dto.ItemDTO;
import me.vasylkov.cs2itemsrestapi.rest.exception.EntityNotFoundException;
import me.vasylkov.cs2itemsrestapi.rest.service.EntityDTOConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/currencyRates")
@RequiredArgsConstructor
public class CurrencyRateController
{
    private final CurrencyRateService rateService;
    private final EntityDTOConverter<CurrencyRate, CurrencyRateDTO> entityDTOConverter;
    private final DbUpdateChecker dbUpdateChecker;

    @GetMapping("/singleRate")
    public CurrencyRateDTO getCurrencyRate(@RequestParam(name = "currencyCode", required = false, defaultValue = "USD") CurrencyCode currencyCode)
    {
        dbUpdateChecker.throwIfUpdating();
        CurrencyRate currencyRate = rateService.findCurrencyRateByCurrencyCode(currencyCode);
        return entityDTOConverter.convertToDto(currencyRate, true);
    }
}

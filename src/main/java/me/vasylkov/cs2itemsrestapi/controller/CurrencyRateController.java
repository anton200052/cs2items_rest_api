package me.vasylkov.cs2itemsrestapi.controller;

import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.model.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.service.CurrencyRateService;
import me.vasylkov.cs2itemsrestapi.component.DbUpdateChecker;
import me.vasylkov.cs2itemsrestapi.component.CurrencyTableUpdater;
import me.vasylkov.cs2itemsrestapi.component.TableUpdater;
import me.vasylkov.cs2itemsrestapi.dto.CurrencyRateDTO;
import me.vasylkov.cs2itemsrestapi.component.EntityDTOConverter;
import me.vasylkov.cs2itemsrestapi.component.ResponseEntityConverter;
import me.vasylkov.cs2itemsrestapi.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
public class CurrencyRateController
{
    @Qualifier("steamInvHelperCurrencyService")
    private final CurrencyRateService rateService;
    private final EntityDTOConverter<CurrencyRate, CurrencyRateDTO> entityDTOConverter;
    private final DbUpdateChecker dbUpdateChecker;
    private final ResponseEntityConverter<CurrencyRateDTO> converter;
    private final ResponseEntityConverter<MessageDTO> messageConverter;
    @Qualifier("currencyTableUpdater")
    private final TableUpdater currencyUpdater;

    public CurrencyRateController(CurrencyRateService rateService, EntityDTOConverter<CurrencyRate, CurrencyRateDTO> entityDTOConverter, DbUpdateChecker dbUpdateChecker, ResponseEntityConverter<CurrencyRateDTO> converter, ResponseEntityConverter<MessageDTO> messageConverter, CurrencyTableUpdater currencyUpdater)
    {
        this.rateService = rateService;
        this.entityDTOConverter = entityDTOConverter;
        this.dbUpdateChecker = dbUpdateChecker;
        this.converter = converter;
        this.messageConverter = messageConverter;
        this.currencyUpdater = currencyUpdater;
    }

    @GetMapping("/single")
    public ResponseEntity<?> getCurrencyRate(@RequestParam(name = "currencyCode", required = false, defaultValue = "USD") CurrencyCode currencyCode)
    {
        dbUpdateChecker.throwIfUpdating();
        CurrencyRate currencyRate = rateService.findCurrencyRateByCurrencyCode(currencyCode);
        CurrencyRateDTO currencyRateDTO = entityDTOConverter.convertToDto(currencyRate);

        return converter.convertToResponseEntity(currencyRateDTO, HttpStatus.OK);
    }

    @GetMapping("/update")
    public ResponseEntity<?> updateRates()
    {
        dbUpdateChecker.throwIfUpdating();
        currencyUpdater.updateTable();
        return messageConverter.convertToResponseEntity(new MessageDTO("Success"), HttpStatus.OK);
    }
}


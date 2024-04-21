package me.vasylkov.cs2itemsrestapi.rest.service;

import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.rest.dto.CurrencyRateDTO;
import me.vasylkov.cs2itemsrestapi.rest.dto.ItemDTO;
import org.springframework.stereotype.Service;

@Service
public class CurrencyRateToDtoConverter implements EntityDTOConverter<CurrencyRate, CurrencyRateDTO>
{
    @Override
    public CurrencyRateDTO convertToDto(CurrencyRate currencyRate, boolean success)
    {
        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO();
        currencyRateDTO.setSuccess(success);
        currencyRateDTO.setCurrencyCode(currencyRate.getCurrencyCode());
        currencyRateDTO.setRate(currencyRate.getRate());
        currencyRateDTO.setLastUpdated(currencyRate.getLastUpdated());
        return currencyRateDTO;
    }
}

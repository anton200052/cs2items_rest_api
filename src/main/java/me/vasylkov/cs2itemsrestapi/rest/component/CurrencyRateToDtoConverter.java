package me.vasylkov.cs2itemsrestapi.rest.component;

import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.rest.dto.CurrencyRateDTO;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRateToDtoConverter implements EntityDTOConverter<CurrencyRate, CurrencyRateDTO>
{
    @Override
    public CurrencyRateDTO convertToDto(CurrencyRate currencyRate)
    {
        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO();
        currencyRateDTO.setCurrencyCode(currencyRate.getCurrencyCode());
        currencyRateDTO.setRate(currencyRate.getRate());
        currencyRateDTO.setLastUpdated(currencyRate.getLastUpdated());
        return currencyRateDTO;
    }
}

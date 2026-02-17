package me.vasylkov.cs2itemsrestapi.component;

import me.vasylkov.cs2itemsrestapi.model.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.dto.CurrencyRateDTO;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRateToDtoConverter implements EntityDTOConverter<CurrencyRate, CurrencyRateDTO>
{
    @Override
    public CurrencyRateDTO convertToDto(CurrencyRate currencyRate)
    {
        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO();
        currencyRateDTO.setChangeRate(currencyRate.getChangeRate());
        currencyRateDTO.setBase(currencyRate.getBase());
        currencyRateDTO.setChange(currencyRate.getChange());
        return currencyRateDTO;
    }
}

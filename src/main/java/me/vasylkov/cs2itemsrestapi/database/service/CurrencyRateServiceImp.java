package me.vasylkov.cs2itemsrestapi.database.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.dao.CurrencyRateRepository;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.entity.Price;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImp implements CurrencyRateService
{
    private final CurrencyRateRepository currencyRateRepository;

    @Override
    public void updateCurrencyRates(List<CurrencyRate> currencyRates)
    {
        Map<CurrencyCode, CurrencyRate> existingItems = findAllCurrencyRates().stream().collect(Collectors.toMap(CurrencyRate::getCurrencyCode, Function.identity()));

        List<CurrencyRate> toBeSaved = new ArrayList<>();
        int updatedCount = 0;
        for (CurrencyRate currencyRate : currencyRates)
        {
            CurrencyRate dbCurrencyRate = existingItems.getOrDefault(currencyRate.getCurrencyCode(), new CurrencyRate());
            if (!dbCurrencyRate.equals(currencyRate))
            {
                dbCurrencyRate.setCurrencyCode(currencyRate.getCurrencyCode());
                dbCurrencyRate.setRate(currencyRate.getRate());
                dbCurrencyRate.setLastUpdated(currencyRate.getLastUpdated());

                toBeSaved.add(dbCurrencyRate);
                updatedCount++;
            }
        }

        if (!toBeSaved.isEmpty())
        {
            saveAllCurrencyRates(toBeSaved);
        }

        System.out.println("Updated " + updatedCount + " currency rates.");
    }

    @Override
    public void saveAllCurrencyRates(List<CurrencyRate> currencyRates)
    {
        currencyRateRepository.saveAll(currencyRates);
    }

    @Override
    public CurrencyRate findCurrencyRateById(long id)
    {
        return currencyRateRepository.findById(id).orElse(null);
    }

    @Override
    public CurrencyRate findCurrencyRateByCurrencyCode(CurrencyCode currencyCode)
    {
        return currencyRateRepository.findByCurrencyCode(currencyCode).orElse(null);
    }

    @Override
    public List<CurrencyRate> findAllCurrencyRates()
    {
        return currencyRateRepository.findAll();
    }
}

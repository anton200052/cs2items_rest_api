package me.vasylkov.cs2itemsrestapi.database.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.dao.CurrencyRateRepository;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.entity.Price;
import me.vasylkov.cs2itemsrestapi.rest.exception.EntityNotFoundException;
import org.slf4j.Logger;
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
    private final Logger logger;

    @Override
    @Transactional
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

        logger.info("Updated {} currency rates.", updatedCount);
    }

    @Override
    @Transactional
    public void saveAllCurrencyRates(List<CurrencyRate> currencyRates)
    {
        currencyRateRepository.saveAll(currencyRates);
    }

    @Override
    @Transactional
    public CurrencyRate findCurrencyRateById(long id)
    {
        return currencyRateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Currency rate with id " + id + " not found"));
    }

    @Override
    @Transactional
    public CurrencyRate findCurrencyRateByCurrencyCode(CurrencyCode currencyCode)
    {
        return currencyRateRepository.findByCurrencyCode(currencyCode).orElseThrow(() -> new EntityNotFoundException("Currency rate with code " + currencyCode + " not found"));
    }

    @Override
    @Transactional
    public List<CurrencyRate> findAllCurrencyRates()
    {
        return currencyRateRepository.findAll();
    }
}

package me.vasylkov.cs2itemsrestapi.database.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.dao.SteamWebApiCurrencyRateRepository;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiCurrencyRate;
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
public class SteamWebApiCurrencyService implements CurrencyRateService
{
    private final SteamWebApiCurrencyRateRepository steamWebApiCurrencyRateRepository;
    private final Logger logger;

    @Override
    @Transactional
    public void updateCurrencyRates(List<CurrencyRate> currencyRates)
    {
        Map<CurrencyCode, CurrencyRate> existingItems = findAllCurrencyRates().stream().collect(Collectors.toMap(CurrencyRate::getChange, Function.identity()));

        List<CurrencyRate> toBeSaved = new ArrayList<>();
        int updatedCount = 0;
        for (CurrencyRate currencyRate : currencyRates)
        {
            CurrencyRate dbCurrencyRate = existingItems.getOrDefault(currencyRate.getChange(), new SteamWebApiCurrencyRate());
            if (!dbCurrencyRate.equals(currencyRate))
            {
                dbCurrencyRate.setChangeRate(currencyRate.getChangeRate());
                dbCurrencyRate.setBase(currencyRate.getBase());
                dbCurrencyRate.setChange(currencyRate.getChange());

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
        List<SteamWebApiCurrencyRate> steamCurrencyRates = currencyRates.stream()
                .map(currencyRate -> (SteamWebApiCurrencyRate) currencyRate)
                .collect(Collectors.toList());
        steamWebApiCurrencyRateRepository.saveAll(steamCurrencyRates);
    }

    @Override
    @Transactional
    public CurrencyRate findCurrencyRateById(long id)
    {
        return steamWebApiCurrencyRateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Currency rate with id " + id + " not found"));
    }

    @Override
    @Transactional
    public CurrencyRate findCurrencyRateByCurrencyCode(CurrencyCode currencyCode)
    {
        return steamWebApiCurrencyRateRepository.findByChange(currencyCode).orElseThrow(() -> new EntityNotFoundException("Currency rate with code " + currencyCode + " not found"));
    }

    @Override
    @Transactional
    public List<CurrencyRate> findAllCurrencyRates()
    {
        return new ArrayList<>(steamWebApiCurrencyRateRepository.findAll());
    }
}

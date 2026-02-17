package me.vasylkov.cs2itemsrestapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.repository.SteamApisCurrencyRateRepository;
import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.model.CurrencyRate;
import me.vasylkov.cs2itemsrestapi.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamInvHelperCurrencyService implements CurrencyRateService {
    private final SteamApisCurrencyRateRepository steamApisCurrencyRateRepository;
    private final Logger logger;

    @Override
    @Transactional
    public void updateCurrencyRates(List<CurrencyRate> currencyRates) {
        Map<CurrencyCode, CurrencyRate> existingItems = findAllCurrencyRates().stream().collect(Collectors.toMap(CurrencyRate::getChange, Function.identity()));

        List<CurrencyRate> toBeSaved = new ArrayList<>();
        int updatedCount = 0;
        for (CurrencyRate currencyRate : currencyRates) {
            CurrencyRate dbCurrencyRate = existingItems.getOrDefault(currencyRate.getChange(), new CurrencyRate());
            if (!dbCurrencyRate.equals(currencyRate)) {
                dbCurrencyRate.setChangeRate(currencyRate.getChangeRate());
                dbCurrencyRate.setBase(currencyRate.getBase());
                dbCurrencyRate.setChange(currencyRate.getChange());

                toBeSaved.add(dbCurrencyRate);
                updatedCount++;
            }
        }

        if (!toBeSaved.isEmpty()) {
            saveAllCurrencyRates(toBeSaved);
        }

        logger.info("Updated {} currency rates.", updatedCount);
    }

    @Override
    @Transactional
    public void saveAllCurrencyRates(List<CurrencyRate> currencyRates) {
        List<CurrencyRate> steamCurrencyRates = currencyRates.stream().map(currencyRate -> (CurrencyRate) currencyRate).collect(Collectors.toList());
        steamApisCurrencyRateRepository.saveAll(steamCurrencyRates);
    }

    @Override
    @Transactional
    public CurrencyRate findCurrencyRateById(long id) {
        return steamApisCurrencyRateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Currency rate with id " + id + " not found"));
    }

    @Override
    @Transactional
    public CurrencyRate findCurrencyRateByCurrencyCode(CurrencyCode currencyCode) {
        return steamApisCurrencyRateRepository.findByChange(currencyCode).orElseThrow(() -> new EntityNotFoundException("Currency rate with code " + currencyCode + " not found"));
    }

    @Override
    @Transactional
    public List<CurrencyRate> findAllCurrencyRates() {
        return new ArrayList<>(steamApisCurrencyRateRepository.findAll());
    }
}

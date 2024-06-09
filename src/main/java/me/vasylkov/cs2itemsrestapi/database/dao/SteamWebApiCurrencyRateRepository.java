package me.vasylkov.cs2itemsrestapi.database.dao;

import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiCurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SteamWebApiCurrencyRateRepository extends JpaRepository<SteamWebApiCurrencyRate, Long>
{
    Optional<SteamWebApiCurrencyRate> findByChange(CurrencyCode changeCode);
}

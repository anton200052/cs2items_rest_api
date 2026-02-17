package me.vasylkov.cs2itemsrestapi.repository;

import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SteamApisCurrencyRateRepository extends JpaRepository<CurrencyRate, Long>
{
    Optional<CurrencyRate> findByChange(CurrencyCode changeCode);
}


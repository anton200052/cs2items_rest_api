package me.vasylkov.cs2itemsrestapi.database.dao;

import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long>
{
    Optional<CurrencyRate> findByCurrencyCode(CurrencyCode currencyCode);
}

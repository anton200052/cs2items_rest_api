package me.vasylkov.cs2itemsrestapi.database.dao;

import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SteamWebApiItemRepository extends JpaRepository<SteamWebApiItem, Integer>
{
    Optional<SteamWebApiItem> findByMarketHashName(String marketName);
}

package me.vasylkov.cs2itemsrestapi.repository;

import me.vasylkov.cs2itemsrestapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SteamApisItemRepository extends JpaRepository<Item, Integer>
{
    Optional<Item> findByMarketHashName(String marketName);
}


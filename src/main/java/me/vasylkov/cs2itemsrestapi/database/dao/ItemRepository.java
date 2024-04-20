package me.vasylkov.cs2itemsrestapi.database.dao;

import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>
{
    Optional<Item> findByName(String name);
}

package me.vasylkov.cs2itemsrestapi.rest;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemDataController
{
    private final ItemService itemService;

    @GetMapping("/{itemName}")
    public Item getItem(@PathVariable("itemName") String itemName)
    {
        return itemService.findItemByName(itemName);
    }
}

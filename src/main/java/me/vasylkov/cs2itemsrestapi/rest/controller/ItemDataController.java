package me.vasylkov.cs2itemsrestapi.rest.controller;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.service.ItemService;
import me.vasylkov.cs2itemsrestapi.db_updater.component.DbUpdateChecker;
import me.vasylkov.cs2itemsrestapi.rest.dto.ItemDTO;
import me.vasylkov.cs2itemsrestapi.rest.service.CurrencyConversionService;
import me.vasylkov.cs2itemsrestapi.rest.service.EntityDTOConverter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemDataController
{
    private final ItemService itemService;
    private final EntityDTOConverter<Item, ItemDTO> entityDTOConverter;
    private final DbUpdateChecker dbUpdateChecker;
    private final CurrencyConversionService currencyConversionService;

    @GetMapping("/singleItem")
    public ItemDTO getItem(@RequestParam(name = "hashName", required = true) String name, @RequestParam(name = "currencyCode", required = false, defaultValue = "USD") CurrencyCode currencyCode)
    {
        dbUpdateChecker.throwIfUpdating();

        Item item = itemService.findItemByName(name);
        ItemDTO itemDTO = entityDTOConverter.convertToDto(item, true);
        currencyConversionService.convertPrice(itemDTO.getPrice(), currencyCode);
        return itemDTO;
    }
}

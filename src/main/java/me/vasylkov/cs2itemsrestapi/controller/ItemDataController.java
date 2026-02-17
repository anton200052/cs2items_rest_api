package me.vasylkov.cs2itemsrestapi.controller;

import me.vasylkov.cs2itemsrestapi.model.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.model.Item;
import me.vasylkov.cs2itemsrestapi.service.ItemService;
import me.vasylkov.cs2itemsrestapi.component.DbUpdateChecker;
import me.vasylkov.cs2itemsrestapi.component.ItemsTableUpdater;
import me.vasylkov.cs2itemsrestapi.component.TableUpdater;
import me.vasylkov.cs2itemsrestapi.component.PriceConverterByCurrencyRate;
import me.vasylkov.cs2itemsrestapi.dto.ItemDTO;
import me.vasylkov.cs2itemsrestapi.component.EntityDTOConverter;
import me.vasylkov.cs2itemsrestapi.component.ResponseEntityConverter;
import me.vasylkov.cs2itemsrestapi.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class ItemDataController
{
    @Qualifier("steamApisItemService")
    private final ItemService itemService;
    private final EntityDTOConverter<Item, ItemDTO> entityDTOConverter;
    private final DbUpdateChecker dbUpdateChecker;
    private final ResponseEntityConverter<ItemDTO> itemDTOResponseEntityConverter;
    private final ResponseEntityConverter<MessageDTO> messageConverter;
    @Qualifier("itemsTableUpdater")
    private final TableUpdater itemsUpdater;
    private final PriceConverterByCurrencyRate priceConverter;

    public ItemDataController(ItemService itemService, EntityDTOConverter<Item, ItemDTO> entityDTOConverter, DbUpdateChecker dbUpdateChecker, ResponseEntityConverter<ItemDTO> itemDTOResponseEntityConverter, ResponseEntityConverter<MessageDTO> messageConverter, ItemsTableUpdater itemsUpdater, PriceConverterByCurrencyRate priceConverter)
    {
        this.itemService = itemService;
        this.entityDTOConverter = entityDTOConverter;
        this.dbUpdateChecker = dbUpdateChecker;
        this.itemDTOResponseEntityConverter = itemDTOResponseEntityConverter;
        this.messageConverter = messageConverter;
        this.itemsUpdater = itemsUpdater;
        this.priceConverter = priceConverter;
    }

    @GetMapping("/single")
    public ResponseEntity<?> getItem(@RequestParam(name = "hashName", required = true) String name, @RequestParam(name = "currencyCode", required = false, defaultValue = "USD") CurrencyCode currencyCode)
    {
        dbUpdateChecker.throwIfUpdating();
        Item item = itemService.findItemByName(name);

        if (currencyCode != null)
        {
            priceConverter.convertPrice(item, currencyCode);
        }

        ItemDTO itemDTO = entityDTOConverter.convertToDto(item);

        return itemDTOResponseEntityConverter.convertToResponseEntity(itemDTO, HttpStatus.OK);
    }

    @GetMapping("/update")
    public ResponseEntity<?> updateItems()
    {
        dbUpdateChecker.throwIfUpdating();
        itemsUpdater.updateTable();
        return messageConverter.convertToResponseEntity(new MessageDTO("Success"), HttpStatus.OK);
    }
}


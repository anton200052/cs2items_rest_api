package me.vasylkov.cs2itemsrestapi.rest.controller;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiItem;
import me.vasylkov.cs2itemsrestapi.database.service.ItemService;
import me.vasylkov.cs2itemsrestapi.db_updater.component.DbUpdateChecker;
import me.vasylkov.cs2itemsrestapi.db_updater.component.SteamWebApiItemsUpdater;
import me.vasylkov.cs2itemsrestapi.db_updater.component.TableUpdater;
import me.vasylkov.cs2itemsrestapi.rest.component.PriceConverterByCurrencyRate;
import me.vasylkov.cs2itemsrestapi.rest.dto.ItemDTO;
import me.vasylkov.cs2itemsrestapi.rest.component.EntityDTOConverter;
import me.vasylkov.cs2itemsrestapi.rest.component.ResponseEntityConverter;
import me.vasylkov.cs2itemsrestapi.rest.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class ItemDataController
{
    @Qualifier("steamWebApiItemService")
    private final ItemService itemService;
    private final EntityDTOConverter<Item, ItemDTO> entityDTOConverter;
    private final DbUpdateChecker dbUpdateChecker;
    private final ResponseEntityConverter<ItemDTO> itemDTOResponseEntityConverter;
    private final ResponseEntityConverter<MessageDTO> messageConverter;
    @Qualifier("steamWebApiItemsUpdater")
    private final TableUpdater itemsUpdater;
    private final PriceConverterByCurrencyRate priceConverter;

    public ItemDataController(ItemService itemService, EntityDTOConverter<Item, ItemDTO> entityDTOConverter, DbUpdateChecker dbUpdateChecker, ResponseEntityConverter<ItemDTO> itemDTOResponseEntityConverter, ResponseEntityConverter<MessageDTO> messageConverter, SteamWebApiItemsUpdater itemsUpdater, PriceConverterByCurrencyRate priceConverter)
    {
        this.itemService = itemService;
        this.entityDTOConverter = entityDTOConverter;
        this.dbUpdateChecker = dbUpdateChecker;
        this.itemDTOResponseEntityConverter = itemDTOResponseEntityConverter;
        this.messageConverter = messageConverter;
        this.itemsUpdater = itemsUpdater;
        this.priceConverter = priceConverter;
    }

    @GetMapping("/singleItem")
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

    @GetMapping("/updateItems")
    public ResponseEntity<?> updateItems()
    {
        dbUpdateChecker.throwIfUpdating();
        itemsUpdater.updateTable();
        return messageConverter.convertToResponseEntity(new MessageDTO("Success"), HttpStatus.OK);
    }
}

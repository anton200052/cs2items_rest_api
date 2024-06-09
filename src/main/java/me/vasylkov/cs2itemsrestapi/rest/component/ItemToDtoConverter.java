package me.vasylkov.cs2itemsrestapi.rest.component;

import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.database.entity.SteamWebApiItem;
import me.vasylkov.cs2itemsrestapi.rest.dto.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemToDtoConverter implements EntityDTOConverter<Item, ItemDTO>
{
    @Override
    public ItemDTO convertToDto(Item steamWebApiItem)
    {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setMarketHashName(steamWebApiItem.getMarketHashName());
        itemDTO.setPriceLatest(steamWebApiItem.getPriceLatest());
        itemDTO.setPriceReal(steamWebApiItem.getPriceReal());
        itemDTO.setPriceMedian(steamWebApiItem.getPriceMedian());
        itemDTO.setPriceAvg(steamWebApiItem.getPriceAvg());
        itemDTO.setPriceMax(steamWebApiItem.getPriceMax());
        itemDTO.setPriceMin(steamWebApiItem.getPriceMin());
        return itemDTO;
    }
}

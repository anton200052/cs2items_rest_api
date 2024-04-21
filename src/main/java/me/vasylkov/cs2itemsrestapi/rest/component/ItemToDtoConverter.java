package me.vasylkov.cs2itemsrestapi.rest.component;

import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.rest.dto.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemToDtoConverter implements EntityDTOConverter<Item, ItemDTO>
{
    @Override
    public ItemDTO convertToDto(Item item)
    {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(item.getName());
        itemDTO.setMarketable(item.getMarketable());
        itemDTO.setTradable(item.getTradable());
        itemDTO.setClassId(item.getClassId());
        itemDTO.setType(item.getType());
        itemDTO.setRarity(item.getRarity());
        itemDTO.setRarityColor(item.getRarityColor());
        itemDTO.setFirstSaleDate(item.getFirstSaleDate());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
}

package me.vasylkov.cs2itemsrestapi.rest.service;

import me.vasylkov.cs2itemsrestapi.database.entity.Item;
import me.vasylkov.cs2itemsrestapi.rest.dto.ItemDTO;
import org.springframework.stereotype.Service;

@Service
public class ItemToDtoConverter implements EntityDTOConverter<Item, ItemDTO>
{
    @Override
    public ItemDTO convertToDto(Item item, boolean success)
    {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setSuccess(success);
        itemDTO.setName(item.getName());
        itemDTO.setMarketable(item.isMarketable());
        itemDTO.setTradable(item.isTradable());
        itemDTO.setClassId(item.getClassId());
        itemDTO.setType(item.getType());
        itemDTO.setRarity(item.getRarity());
        itemDTO.setRarityColor(item.getRarityColor());
        itemDTO.setFirstSaleDate(item.getFirstSaleDate());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
}

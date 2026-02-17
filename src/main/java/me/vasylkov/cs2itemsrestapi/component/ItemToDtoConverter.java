package me.vasylkov.cs2itemsrestapi.component;

import me.vasylkov.cs2itemsrestapi.model.Item;
import me.vasylkov.cs2itemsrestapi.dto.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemToDtoConverter implements EntityDTOConverter<Item, ItemDTO>
{
    @Override
    public ItemDTO convertToDto(Item steamApisItem)
    {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setMarketHashName(steamApisItem.getMarketHashName());
        itemDTO.setImage(steamApisItem.getImage());
        itemDTO.setNameId(steamApisItem.getNameId());

        if (steamApisItem.getPrices() != null) {
            itemDTO.setPriceLatest(steamApisItem.getPrices().getLatest());
            itemDTO.setPriceReal(steamApisItem.getPrices().getSafe());
            itemDTO.setPriceMedian(steamApisItem.getPrices().getMedian());
            itemDTO.setPriceAvg(steamApisItem.getPrices().getAvg());
            itemDTO.setPriceMax(steamApisItem.getPrices().getMax());
            itemDTO.setPriceMin(steamApisItem.getPrices().getMin());
        }
        
        return itemDTO;
    }
}

package me.vasylkov.cs2itemsrestapi.cs_backpack_api.mapping;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vasylkov.cs2itemsrestapi.database.entity.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemListDeserializer extends JsonDeserializer<List<Item>>
{
    @Override
    public List<Item> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        System.out.println(2);
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        Map<String, Item> map = mapper.readValue(p, new TypeReference<>()
        {
        });
        List<Item> items = new ArrayList<>();
        for (Item item : map.values())
        {
            if (item.getPrice() != null)
            {
                item.getPrice().setItem(item);
                items.add(item);
            }
        }
        return items;
    }

}


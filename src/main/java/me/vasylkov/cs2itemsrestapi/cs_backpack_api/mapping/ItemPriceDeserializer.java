package me.vasylkov.cs2itemsrestapi.cs_backpack_api.mapping;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vasylkov.cs2itemsrestapi.database.entity.Price;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class ItemPriceDeserializer extends JsonDeserializer<Price>
{
    @Override
    public Price deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        Map<String, Price> map = mapper.readValue(p, new TypeReference<>()
        {
        });
        for (Price price : map.values())
        {
            if ((price.getAverage() != null && price.getAverage().compareTo(BigDecimal.ZERO) != 0) || (price.getMedian() != null && price.getMedian().compareTo(BigDecimal.ZERO) != 0) || (price.getLowestPrice() != null && price.getLowestPrice().compareTo(BigDecimal.ZERO) != 0) || (price.getHighestPrice() != null && price.getHighestPrice().compareTo(BigDecimal.ZERO) != 0) || (price.getSold() != null && !price.getSold().equals(0)))
            {
                return price;
            }
        }
        return null;
    }
}

package me.vasylkov.cs2itemsrestapi.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class CurrencyRateDTO extends DataTransferObject
{
    @JsonProperty("currency_code")
    private CurrencyCode currencyCode;


    private BigDecimal rate;

    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;
}

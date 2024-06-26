package me.vasylkov.cs2itemsrestapi.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.vasylkov.cs2itemsrestapi.database.entity.CurrencyCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CurrencyRateDTO
{
    private CurrencyCode base;

    private CurrencyCode change;

    private BigDecimal changeRate;
}

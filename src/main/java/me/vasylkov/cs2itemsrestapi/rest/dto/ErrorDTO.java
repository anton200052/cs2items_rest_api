package me.vasylkov.cs2itemsrestapi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ErrorDTO extends DataTransferObject
{
    private final String reason;
}

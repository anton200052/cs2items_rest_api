package me.vasylkov.cs2itemsrestapi.rest.service;

public interface EntityDTOConverter<Entity, Dto>
{
    Dto convertToDto(Entity entity, boolean success);
}

package me.vasylkov.cs2itemsrestapi.rest.component;

public interface EntityDTOConverter<Entity, Dto>
{
    Dto convertToDto(Entity entity);
}

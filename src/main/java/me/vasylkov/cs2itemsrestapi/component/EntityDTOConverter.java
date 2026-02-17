package me.vasylkov.cs2itemsrestapi.component;

public interface EntityDTOConverter<Entity, Dto>
{
    Dto convertToDto(Entity entity);
}

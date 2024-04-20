package me.vasylkov.cs2itemsrestapi.database.entity;

public enum CurrencyCode
{
    USD,
    UAH;

    @Override
    public String toString() {
        return name();
    }
}

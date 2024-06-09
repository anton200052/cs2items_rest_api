package me.vasylkov.cs2itemsrestapi.database.entity;

public enum CurrencyCode
{
    UAH,
    USD;

    @Override
    public String toString() {
        return name();
    }
}

package me.vasylkov.cs2itemsrestapi.database.entity;

public enum CurrencyCode
{
    EUR,
    KZT,
    PLN,
    RUB,
    UAH,
    USD;

    @Override
    public String toString() {
        return name();
    }
}

package me.vasylkov.cs2itemsrestapi.database.entity;

public enum CurrencyCode {
    UAH,
    USD,
    GBP,
    EUR,
    CHF,
    RUB,
    PLN,
    BRL,
    JPY,
    NOK,
    IDR,
    MYR,
    PHP,
    SGD,
    THB,
    VND,
    KRW,
    TRY,
    MXN,
    CAD,
    AUD,
    NZD,
    CNY,
    INR,
    CLP,
    PEN,
    COP,
    ZAR,
    HKD,
    TWD,
    SAR,
    AED,
    ARS,
    ILS,
    KZT,
    KWD,
    QAR,
    CRC,
    UYU,
    VEF;

    @Override
    public String toString() {
        return name();
    }
}

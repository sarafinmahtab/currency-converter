package com.sarafinmahtab.currencyconverter.data.domain

const val DefaultBaseCurrencyCode = "USD"

data class CountryCurrency(
    val id: Int,
    val country: String,
    val currency: String,
    val currencyCode: String,
    val symbol: Char,
    val hasCurrencySymbol: Boolean,
    val flag: String,
    val currentRateByBase: Double? = 0.0,
) {
    constructor(countryCurrency: CountryCurrency, rate: Double) : this(
        countryCurrency.id,
        countryCurrency.country,
        countryCurrency.currency,
        countryCurrency.currencyCode,
        countryCurrency.symbol,
        countryCurrency.hasCurrencySymbol,
        countryCurrency.flag
    )
}

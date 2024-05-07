package com.sarafinmahtab.currencyconverter.data.domain

const val DefaultBaseCurrencyCode = "USD"

data class CountryCurrency(
    val code: String,
    val name: String,
    val country: String,
    val countryCode: String,
    val flag: String,
    val currentRateByBase: Double? = 0.0,
) {
    constructor(countryCurrency: CountryCurrency, rate: Double) : this(
        countryCurrency.code,
        countryCurrency.name,
        countryCurrency.country,
        countryCurrency.countryCode,
        countryCurrency.flag,
        rate,
    )

    fun formattedName() = "$country ($name)"
}

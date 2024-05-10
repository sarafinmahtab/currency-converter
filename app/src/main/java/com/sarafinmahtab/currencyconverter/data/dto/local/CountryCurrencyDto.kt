package com.sarafinmahtab.currencyconverter.data.dto.local

import com.google.gson.annotations.SerializedName
import com.sarafinmahtab.currencyconverter.data.domain.CountryCurrency
import com.sarafinmahtab.currencyconverter.data.dto.DtoToDomainMapper


/*
 * Created by Arafin Mahtab on 11/10/2021.
 */

/**
 * "id": 68,
 *     "name": "Ethiopia",
 *     "isoAlpha2": "ET",
 *     "isoAlpha3": "ETH",
 *     "isoNumeric": 231,
 *     "currency": {
 *       "code": "ETB",
 *       "name": "Birr",
 *       "symbol": false
 *     },
 */
data class CountryCurrencyDto(
    @field:SerializedName("code")
    val code: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("country")
    val country: String,
    @field:SerializedName("countryCode")
    val countryCode: String,
    @field:SerializedName("flag")
    val flag: String,
) : DtoToDomainMapper<CountryCurrency> {
    override fun mapToDomain() = CountryCurrency(
        code = code, name = name, country = country, countryCode = countryCode, flag = flag,
    )
}

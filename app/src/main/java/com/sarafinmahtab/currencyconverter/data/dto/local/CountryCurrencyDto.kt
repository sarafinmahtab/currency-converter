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
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("isoAlpha2")
    val isoAlpha2: String,
    @field:SerializedName("isoAlpha3")
    val isoAlpha3: String,
    @field:SerializedName("isoNumeric")
    val isoNumeric: String,
    @field:SerializedName("currency")
    val currencyDto: CurrencyDto,
    @field:SerializedName("flag")
    val flag: String,
) : DtoToDomainMapper<CountryCurrency> {
    override fun mapToDomain() = CountryCurrency(
        id = id,
        country = name,
        currency = currencyDto.name,
        currencyCode = currencyDto.code,
        symbol = if (currencyDto.symbol is Boolean) '0' else currencyDto.symbol.toString().first(),
        hasCurrencySymbol = currencyDto.symbol !is Boolean,
        flag = flag
    )
}

data class CurrencyDto(
    @field:SerializedName("code")
    val code: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("symbol")
    val symbol: Any,
)

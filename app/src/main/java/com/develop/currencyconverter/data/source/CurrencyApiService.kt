package com.develop.currencyconverter.data.source

import com.develop.currencyconverter.data.parameters.CurrencyType
import com.develop.currencyconverter.model.CurrencyConversionResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

interface CurrencyApiService {

    @GET("currency/convert")
    suspend fun convertCurrency(
        @Header("X-RapidAPI-Key") rapidApiKey: String,
        @Header("X-RapidAPI-Host") rapidAPiHost: String,
        @Query("format") responseFormat: String,
        @CurrencyType @Query("from") fromCurrency: String,
        @CurrencyType @Query("to") toCurrency: String,
        @Query("amount") amountToConvert: Double
    ): CurrencyConversionResult
}

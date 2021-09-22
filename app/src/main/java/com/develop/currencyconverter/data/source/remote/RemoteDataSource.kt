package com.develop.currencyconverter.data.source.remote

import com.develop.currencyconverter.data.model.CurrencyConversionResult

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

interface RemoteDataSource {

    suspend fun convertCurrency(
        rapidApiKey: String,
        rapidAPiHost: String,
        responseFormat: String,
        fromCurrency: String,
        toCurrency: String,
        amountToConvert: Double
    ): CurrencyConversionResult
}

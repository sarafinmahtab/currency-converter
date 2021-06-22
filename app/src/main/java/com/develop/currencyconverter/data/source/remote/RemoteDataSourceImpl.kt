package com.develop.currencyconverter.data.source.remote

import com.develop.currencyconverter.data.source.CurrencyApiService
import com.develop.currencyconverter.model.CurrencyConversionResult
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val currencyApiService: CurrencyApiService
) : RemoteDataSource {

    override suspend fun convertCurrency(
        rapidApiKey: String,
        rapidAPiHost: String,
        responseFormat: String,
        fromCurrency: String,
        toCurrency: String,
        amountToConvert: Double
    ): CurrencyConversionResult {

        return currencyApiService.convertCurrency(
            rapidApiKey, rapidAPiHost, responseFormat, fromCurrency, toCurrency, amountToConvert
        )
    }
}

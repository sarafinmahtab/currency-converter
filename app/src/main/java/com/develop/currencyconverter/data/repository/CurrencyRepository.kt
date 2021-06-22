package com.develop.currencyconverter.data.repository

import com.android.core.helpers.Results
import com.develop.currencyconverter.model.CurrencyConversionResult

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/2021
 */

interface CurrencyRepository {

    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amountToConvert: Double
    ): Results<CurrencyConversionResult>
}

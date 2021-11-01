package com.develop.currencyconverter.data.repository

import com.android.core.helpers.Results
import com.develop.currencyconverter.data.model.CurrencyRates

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/2021
 */

interface CurrencyRepository {

    suspend fun getLatestRates(appId: String, base: String): Results<CurrencyRates>
}

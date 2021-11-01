package com.develop.currencyconverter.data.source.remote

import com.develop.currencyconverter.data.model.CurrencyRates

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

interface RemoteDataSource {

    suspend fun getLatestRates(appId: String, base: String): CurrencyRates
}

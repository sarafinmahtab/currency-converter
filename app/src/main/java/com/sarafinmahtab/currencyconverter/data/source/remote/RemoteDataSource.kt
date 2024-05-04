package com.sarafinmahtab.currencyconverter.data.source.remote

import com.sarafinmahtab.currencyconverter.data.model.api.CurrencyRates

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

interface RemoteDataSource {

    suspend fun getLatestRates(appId: String, base: String): CurrencyRates
}

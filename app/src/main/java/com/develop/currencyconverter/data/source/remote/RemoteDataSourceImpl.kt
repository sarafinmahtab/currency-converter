package com.develop.currencyconverter.data.source.remote

import com.develop.currencyconverter.data.model.CurrencyRates
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

    override suspend fun getLatestRates(
        appId: String,
        base: String,
    ): CurrencyRates {
        return currencyApiService.getLatestRates(appId, base)
    }
}

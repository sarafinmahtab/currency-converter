package com.sarafinmahtab.currencyconverter.data.source.remote

import com.sarafinmahtab.currencyconverter.data.dto.api.CurrencyRatesDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

interface OpenExchangeAPIService {

    @GET("latest.json")
    suspend fun fetchLatestCurrencyRates(
        @Query("app_id") appId: String,
        @Query("base") base: String,
    ): CurrencyRatesDto
}

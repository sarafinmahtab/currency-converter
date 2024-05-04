package com.sarafinmahtab.currencyconverter.data.source.remote

import com.sarafinmahtab.currencyconverter.data.model.api.CurrencyRates
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

interface CurrencyApiService {

    @GET("latest.json")
    suspend fun getLatestRates(
        @Query("app_id") appId: String,
        @Query("base") base: String,
    ): CurrencyRates
}
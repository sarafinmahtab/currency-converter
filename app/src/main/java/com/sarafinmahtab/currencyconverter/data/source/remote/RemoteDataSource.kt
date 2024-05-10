package com.sarafinmahtab.currencyconverter.data.source.remote

import com.android.core.helpers.Results
import com.sarafinmahtab.currencyconverter.BuildConfig
import com.sarafinmahtab.currencyconverter.data.dto.api.CurrencyRatesDto
import com.sarafinmahtab.currencyconverter.helper.APIResolver
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

interface RemoteDataSource {
    suspend fun fetchLatestCurrencyRates(base: String): Results<CurrencyRatesDto>
}

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val openExchangeAPIService: OpenExchangeAPIService,
    private val apiResolver: APIResolver,
) : RemoteDataSource {
    override suspend fun fetchLatestCurrencyRates(
        base: String,
    ): Results<CurrencyRatesDto> =
        apiResolver.resolve("CurrencyRepositoryImpl->fetchLatestCurrencyRates") {
            openExchangeAPIService.fetchLatestCurrencyRates(BuildConfig.OPEN_EXCHANGE_APP_ID, base)
        }
}

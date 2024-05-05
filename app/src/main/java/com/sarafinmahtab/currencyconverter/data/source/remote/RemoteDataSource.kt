package com.sarafinmahtab.currencyconverter.data.source.remote

import com.android.core.helpers.Results
import com.sarafinmahtab.currencyconverter.BuildConfig
import com.sarafinmahtab.currencyconverter.data.dto.api.CurrencyRatesDto
import com.sarafinmahtab.currencyconverter.helper.APIResolver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

interface RemoteDataSource {
    suspend fun getLatestRates(base: String): Results<CurrencyRatesDto>
}

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val openExchangeAPIService: OpenExchangeAPIService,
    private val apiResolver: APIResolver,
) : RemoteDataSource {
    override suspend fun getLatestRates(
        base: String,
    ): Results<CurrencyRatesDto> = withContext(Dispatchers.IO) {
        return@withContext apiResolver.resolve("CurrencyRepositoryImpl->convertCurrency") {
            openExchangeAPIService.getLatestRates(BuildConfig.OPEN_EXCHANGE_APP_ID, base)
        }
    }
}

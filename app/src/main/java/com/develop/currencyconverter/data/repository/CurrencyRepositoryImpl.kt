package com.develop.currencyconverter.data.repository

import com.android.core.helpers.Results
import com.develop.currencyconverter.data.model.CurrencyRates
import com.develop.currencyconverter.data.source.remote.RemoteDataSource
import com.develop.currencyconverter.helper.ResolveApiResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/2021
 */

@ActivityRetainedScoped
class CurrencyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val resolveApiResponse: ResolveApiResponse
) : CurrencyRepository {

    override suspend fun getLatestRates(appId: String, base: String): Results<CurrencyRates> = withContext(Dispatchers.IO) {
        return@withContext resolveApiResponse.resolve("CurrencyRepositoryImpl->convertCurrency") {
            remoteDataSource.getLatestRates(appId, base)
        }
    }
}

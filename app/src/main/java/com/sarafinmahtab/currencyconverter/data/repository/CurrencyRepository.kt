package com.sarafinmahtab.currencyconverter.data.repository

import com.android.core.helpers.Results
import com.sarafinmahtab.currencyconverter.model.api.CurrencyRates
import com.sarafinmahtab.currencyconverter.data.source.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/2021
 */

interface CurrencyRepository {
    suspend fun getLatestRates(base: String): Results<CurrencyRates>
}

@ActivityRetainedScoped
class CurrencyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : CurrencyRepository {
    override suspend fun getLatestRates(base: String): Results<CurrencyRates> =
        remoteDataSource.getLatestRates(base)
}

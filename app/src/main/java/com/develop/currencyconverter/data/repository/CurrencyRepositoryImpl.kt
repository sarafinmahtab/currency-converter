package com.develop.currencyconverter.data.repository

import com.android.core.helpers.Results
import com.develop.currencyconverter.data.ServerConstant
import com.develop.currencyconverter.data.source.remote.RemoteDataSource
import com.develop.currencyconverter.helper.ResolveApiResponse
import com.develop.currencyconverter.data.model.CurrencyConversionResult
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

    override suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amountToConvert: Double
    ): Results<CurrencyConversionResult> = withContext(Dispatchers.IO) {

        return@withContext resolveApiResponse.resolve("CurrencyRepositoryImpl->convertCurrency") {
            remoteDataSource.convertCurrency(
                ServerConstant.RAPID_API_KEY,
                ServerConstant.RAPID_API_HOST,
                ServerConstant.RESPONSE_FORMAT,
                fromCurrency,
                toCurrency,
                amountToConvert
            )
        }
    }
}

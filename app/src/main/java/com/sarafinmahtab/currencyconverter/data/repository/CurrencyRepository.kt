package com.sarafinmahtab.currencyconverter.data.repository

import com.android.core.helpers.Results
import com.sarafinmahtab.currencyconverter.data.model.api.CurrencyRates

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/2021
 */

interface CurrencyRepository {

    suspend fun getLatestRates(appId: String, base: String): Results<CurrencyRates>
}

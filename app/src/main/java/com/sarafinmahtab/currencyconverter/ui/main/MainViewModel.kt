package com.sarafinmahtab.currencyconverter.ui.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core.helpers.Results
import com.sarafinmahtab.currencyconverter.R
import com.sarafinmahtab.currencyconverter.data.AppDataStore.currenciesPerCountryFlag
import com.sarafinmahtab.currencyconverter.model.api.CurrencyRates
import com.sarafinmahtab.currencyconverter.model.local.Country
import com.sarafinmahtab.currencyconverter.data.repository.CurrencyRepository
import com.sarafinmahtab.currencyconverter.helper.getJsonFromRawResource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sarafinmahtab.currencyconverter.data.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/21
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _currencyRates: MutableStateFlow<CurrencyRates> =
        MutableStateFlow(CurrencyRates(base = AppConstant.defaultBaseCurrencyCode))

    private val _liveCurrencyWithFlagList: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList())
    val liveCurrencyWithFlagList: StateFlow<List<String>> = _liveCurrencyWithFlagList

    private val _initialLoading = MutableStateFlow(true)
    val initialLoading: StateFlow<Boolean> = _initialLoading

    fun loadCountriesInfo(resources: Resources) {
        val countriesJson = resources.getJsonFromRawResource(R.raw.countries)
        val countries = Gson().fromJson<ArrayList<Country>>(
            countriesJson,
            object : TypeToken<ArrayList<Country>>() {}.type
        )

        viewModelScope.launch {
            updateCurrenciesPerCountryFlag(countries)
        }
    }

    private suspend fun updateCurrenciesPerCountryFlag(countries: ArrayList<Country>) =
        withContext(Dispatchers.Default) {
            countries.map { country ->
                country.currencies.map {
                    currenciesPerCountryFlag[it.key] = country.flag
                }
            }
        }

    fun getLatestRates() {
        viewModelScope.launch {
            when (val result =
                currencyRepository.getLatestRates(AppConstant.defaultBaseCurrencyCode)) {
                is Results.Success -> {
                    _initialLoading.value = false
                    _liveCurrencyWithFlagList.value = result.value.rates?.keys?.map {
                        if (currenciesPerCountryFlag.containsKey(it)) {
                            "${currenciesPerCountryFlag[it]} $it"
                        } else {
                            it
                        }
                    } ?: emptyList()
                    _currencyRates.value = result.value
                }

                is Results.Failure -> {
                    _initialLoading.value = false
                    _liveCurrencyWithFlagList.value = emptyList()
                }
            }
        }
    }

    fun getCurrencyWithFlag(currencyWithoutFlag: String): String {
        return _liveCurrencyWithFlagList.value.find {
            it.endsWith(currencyWithoutFlag)
        } ?: currencyWithoutFlag
    }

    fun convertCurrency(
        baseCurrency: String,
        baseCurrencyValue: String,
        currentCurrency: String,
        convertedCurrencyAmount: (String) -> Unit
    ) {
        viewModelScope.launch {
            if (!_currencyRates.value.rates.isNullOrEmpty() && baseCurrencyValue.isNotBlank()) {
                val rateForBaseCurrency = _currencyRates.value.rates?.get(baseCurrency) ?: 1.0
                val rateForCurrentCurrency = _currencyRates.value.rates?.get(currentCurrency) ?: 1.0
                convertedCurrencyAmount(
                    (baseCurrencyValue.toDouble()
                        .times(rateForCurrentCurrency / rateForBaseCurrency).toString())
                )
            } else {
                convertedCurrencyAmount((0.0).toString())
            }
        }
    }
}

package com.sarafinmahtab.currencyconverter.ui.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core.helpers.Results
import com.sarafinmahtab.currencyconverter.R
import com.sarafinmahtab.currencyconverter.data.dto.local.CountryCurrencyDto
import com.sarafinmahtab.currencyconverter.data.repository.CurrencyRepository
import com.sarafinmahtab.currencyconverter.helper.getJsonFromRawResource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sarafinmahtab.currencyconverter.data.domain.CountryCurrency
import com.sarafinmahtab.currencyconverter.data.domain.DefaultBaseCurrencyCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/21
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _currencyRates = MutableStateFlow(mapOf<String, Double>())

    private val _liveCountryCurrencies = MutableStateFlow<List<CountryCurrency>>(emptyList())
    val liveCountryCurrencies: StateFlow<List<CountryCurrency>> = _liveCountryCurrencies

    private val _initialLoading = MutableStateFlow(true)
    val initialLoading: StateFlow<Boolean> = _initialLoading

    fun loadCountriesInfo(resources: Resources) {
        viewModelScope.launch(Dispatchers.Default) {
            val countryCurrenciesJson = resources.getJsonFromRawResource(R.raw.country_currencies)
            val countryCurrencies = (Gson().fromJson<ArrayList<CountryCurrencyDto>>(
                countryCurrenciesJson, object : TypeToken<ArrayList<CountryCurrencyDto>>() {}.type
            )).map { it.mapToDomain() }

            _liveCountryCurrencies.update { countryCurrencies }
        }
    }

    fun getLatestRates() {
        viewModelScope.launch {
            when (val result = currencyRepository.getLatestRates(DefaultBaseCurrencyCode)) {
                is Results.Success -> {
                    _initialLoading.update { false }

                    val currenciesWithRates = _liveCountryCurrencies.value
                        .map {
                            if (result.value.rates.containsKey(it.code)) {
                                result.value.rates[it.code]?.let { rate ->
                                    CountryCurrency(it, rate)
                                } ?: CountryCurrency(it, -1.0)
                            } else {
                                CountryCurrency(it, -1.0)
                            }
                        }
                        .filter { it.currentRateByBase != -1.0 }

                    _liveCountryCurrencies.update { currenciesWithRates }
                    _currencyRates.update { result.value.rates }
                }

                is Results.Failure -> {
                    _initialLoading.update { false }
                    _liveCountryCurrencies.update { emptyList() }
                }
            }
        }
    }

    fun getCurrencyFlag(currencyCode: String) = _liveCountryCurrencies.value.find {
        it.code == currencyCode
    }?.flag ?: ""

    fun convertCurrency(
        baseCurrency: String,
        baseCurrencyValue: String,
        currentCurrency: String,
        convertedCurrencyAmount: (String) -> Unit
    ) {
        viewModelScope.launch {
            if (_currencyRates.value.isNotEmpty() && baseCurrencyValue.isNotBlank()) {
                val rateForBaseCurrency = _currencyRates.value[baseCurrency] ?: 1.0
                val rateForCurrentCurrency = _currencyRates.value[currentCurrency] ?: 1.0

                convertedCurrencyAmount(
                    (baseCurrencyValue.toDouble().times(rateForCurrentCurrency / rateForBaseCurrency).toString())
                )
            } else {
                convertedCurrencyAmount((0.0).toString())
            }
        }
    }
}

package com.develop.currencyconverter.ui.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core.helpers.Results
import com.develop.currencyconverter.R
import com.develop.currencyconverter.data.AppStore.currenciesPerCountryFlag
import com.develop.currencyconverter.data.ServerConstant
import com.develop.currencyconverter.data.model.api.CurrencyRates
import com.develop.currencyconverter.data.model.local.Country
import com.develop.currencyconverter.data.repository.CurrencyRepository
import com.develop.currencyconverter.helper.getJsonFromRawResource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    private val _currencyRates: MutableStateFlow<Results<CurrencyRates>> = MutableStateFlow(Results.Success(CurrencyRates()))

    private val _liveCurrencyList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val liveCurrencyList: StateFlow<List<String>> = _liveCurrencyList

    private val _initialLoading = MutableStateFlow(true)
    val initialLoading: StateFlow<Boolean> = _initialLoading

    fun loadCountriesInfo(resources: Resources) {
        val countriesJson = resources.getJsonFromRawResource(R.raw.countries)
        val countries = Gson().fromJson<ArrayList<Country>>(countriesJson, object : TypeToken<ArrayList<Country>>() {}.type)

        viewModelScope.launch {
            updateCurrenciesPerCountryFlag(countries)
        }
    }

    private suspend fun updateCurrenciesPerCountryFlag(countries: ArrayList<Country>) = withContext(Dispatchers.Default) {
        countries.map { country ->
            country.currencies.map {
                currenciesPerCountryFlag[it.key] = country.flag
            }
        }
    }

    fun getLatestRates() {
        viewModelScope.launch {
            when (val result = currencyRepository.getLatestRates(ServerConstant.CURRENCY_API_APP_ID, ServerConstant.BASE_CURRENCY)) {
                is Results.Success -> {
                    _initialLoading.value = false
                    _liveCurrencyList.value = result.value.rates?.keys?.map {
                        if (currenciesPerCountryFlag.containsKey(it)) {
                            "${currenciesPerCountryFlag[it]} $it"
                        } else {
                            it
                        }
                    } ?: emptyList()
                    _currencyRates.value = result
                }
                is Results.Failure -> {
                    _initialLoading.value = false
                    _liveCurrencyList.value = emptyList()
                }
            }
        }
    }
}

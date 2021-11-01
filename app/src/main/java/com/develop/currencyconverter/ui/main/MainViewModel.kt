package com.develop.currencyconverter.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core.helpers.Results
import com.develop.currencyconverter.data.ServerConstant
import com.develop.currencyconverter.data.model.CurrencyRates
import com.develop.currencyconverter.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _currencyRates: MutableStateFlow<Results<CurrencyRates>> = MutableStateFlow(Results.Success(CurrencyRates()))
    val currencyRates: StateFlow<Results<CurrencyRates>> = _currencyRates

    private val _initialLoading = MutableStateFlow(true)
    val initialLoading: StateFlow<Boolean> = _initialLoading

    fun getLatestRates() {
        viewModelScope.launch {
            val result = currencyRepository.getLatestRates(ServerConstant.CURRENCY_API_APP_ID, ServerConstant.BASE_CURRENCY)
            _initialLoading.value = false
            _currencyRates.value = result
        }
    }
}

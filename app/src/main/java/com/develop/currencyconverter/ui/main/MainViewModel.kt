package com.develop.currencyconverter.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core.helpers.Results
import com.develop.currencyconverter.data.repository.CurrencyRepository
import com.develop.currencyconverter.data.model.CurrencyConversionResult
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

    private val _convertedCurrencyResult = MutableStateFlow(Results.Success(CurrencyConversionResult()))
    val convertedCurrencyResult: StateFlow<Results<CurrencyConversionResult>> = _convertedCurrencyResult

    fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amountToConvert: Double
    ) {
        viewModelScope.launch {

        }
    }
}

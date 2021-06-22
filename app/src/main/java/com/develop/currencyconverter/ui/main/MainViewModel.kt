package com.develop.currencyconverter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core.helpers.Results
import com.develop.currencyconverter.data.repository.CurrencyRepository
import com.develop.currencyconverter.model.CurrencyConversionResult
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _currencyConversionResult = MutableLiveData<Results<CurrencyConversionResult>>()
    val currencyConversionResult: LiveData<Results<CurrencyConversionResult>> =
        _currencyConversionResult

    fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amountToConvert: Double
    ) {
        viewModelScope.launch {
            val result =
                currencyRepository.convertCurrency(fromCurrency, toCurrency, amountToConvert)
            _currencyConversionResult.postValue(result)
        }
    }
}

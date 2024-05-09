package com.sarafinmahtab.currencyconverter.ui.main.converter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


/*
 * Created by Arafin Mahtab on 1/10/2022.
 * Copyright (c) 2022 Rangan Apps. All rights reserved.
 */

data class ConverterState(
    val baseCurrency: MutableState<String> = mutableStateOf(DEFAULT_BASE_CURRENCY),
    val baseCurrencyAmount: MutableState<String> = mutableStateOf(DEFAULT_BASE_CURRENCY_VALUE.toString()),
    val currentCurrency: MutableState<String> = mutableStateOf(DEFAULT_CURRENT_CURRENCY),
    val currentCurrencyAmount: MutableState<String> = mutableStateOf(DEFAULT_CURRENT_CURRENCY_VALUE.toString()),
    val currencyDropdownSelectedState: MutableState<Int> = mutableIntStateOf(CurrencyDropdownState.NONE),
)

@Composable
fun rememberConverterState(
    baseCurrency: MutableState<String> = remember { mutableStateOf(DEFAULT_BASE_CURRENCY) },
    baseCurrencyAmount: MutableState<String> = remember { mutableStateOf(DEFAULT_BASE_CURRENCY_VALUE.toString()) },
    currentCurrency: MutableState<String> = remember { mutableStateOf(DEFAULT_CURRENT_CURRENCY) },
    currentCurrencyAmount: MutableState<String> = remember {
        mutableStateOf(
            DEFAULT_CURRENT_CURRENCY_VALUE.toString()
        )
    },
    currencyDropdownSelectedState: MutableState<Int> = remember {
        mutableIntStateOf(
            CurrencyDropdownState.NONE
        )
    },
) = remember(
    baseCurrency,
    baseCurrencyAmount,
    currentCurrency,
    currentCurrencyAmount,
    currencyDropdownSelectedState
) {
    ConverterState(
        baseCurrency,
        baseCurrencyAmount,
        currentCurrency,
        currentCurrencyAmount,
        currencyDropdownSelectedState
    )
}

package com.develop.currencyconverter.ui.main.converter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.main.MainViewModel
import kotlinx.coroutines.launch


/*
 * Created by Arafin Mahtab on 9/22/2021.
 */

const val DEFAULT_BASE_CURRENCY_VALUE = 1.0
const val DEFAULT_CURRENT_CURRENCY_VALUE = 0.0

const val DEFAULT_BASE_CURRENCY = "USD"
const val DEFAULT_CURRENT_CURRENCY = "BDT"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConverterScreen(viewModel: MainViewModel) {
    val currencyList by viewModel.liveCurrencyWithFlagList.collectAsState()

    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val converterState = rememberConverterState()

    val baseCurrencyAmountSelector: (String) -> Unit = {
        with(converterState) {
            baseCurrencyAmount.value = it
            viewModel.convertCurrency(
                baseCurrency.value,
                baseCurrencyAmount.value,
                currentCurrency.value
            ) { convertedCurrencyAmount ->
                currentCurrencyAmount.value = convertedCurrencyAmount
            }
        }
    }

    val currentCurrencyAmountSelector: (String) -> Unit = {
        with(converterState) {
            currentCurrencyAmount.value = it
            viewModel.convertCurrency(
                currentCurrency.value,
                currentCurrencyAmount.value,
                baseCurrency.value
            ) { convertedCurrencyValue ->
                baseCurrencyAmount.value = convertedCurrencyValue
            }
        }
    }

    baseCurrencyAmountSelector(converterState.baseCurrencyAmount.value)

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            Box(modifier = Modifier.defaultMinSize(minHeight = 1.dp)) {
                DropdownCurrencyList(currencies = currencyList) {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                        with(converterState) {
                            when (currencyDropdownSelectedState.value) {
                                CurrencyDropdownState.BASE -> {
                                    baseCurrency.value = it
                                }
                                CurrencyDropdownState.CURRENT -> {
                                    currentCurrency.value = it
                                }
                            }
                            baseCurrencyAmountSelector(baseCurrencyAmount.value)
                        }
                    }
                }
            }
        },
        sheetShape = RoundedCornerShape(topEnd = medium, topStart = medium)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Spacer(modifier = Modifier.padding(25.dp))
            CurrencyPickerWithAmount(
                coroutineScope,
                modalBottomSheetState,
                R.string.from_currency,
                viewModel.getCurrencyWithFlag(converterState.baseCurrency.value),
                converterState.baseCurrencyAmount,
                baseCurrencyAmountSelector
            ) {
                converterState.currencyDropdownSelectedState.value = CurrencyDropdownState.BASE
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            with(converterState) {
                                val tempBaseCurrency = baseCurrency.value
                                baseCurrency.value = currentCurrency.value
                                currentCurrency.value = tempBaseCurrency

                                baseCurrencyAmountSelector(baseCurrencyAmount.value)
                            }
                        }
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_swap_currency),
                        contentDescription = stringResource(R.string.swap_currency)
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                        text = stringResource(id = R.string.swap_currency),
                        style = MaterialTheme.typography.h2
                    )
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            CurrencyPickerWithAmount(
                coroutineScope,
                modalBottomSheetState,
                R.string.to_currency,
                viewModel.getCurrencyWithFlag(converterState.currentCurrency.value),
                converterState.currentCurrencyAmount,
                currentCurrencyAmountSelector
            ) {
                converterState.currencyDropdownSelectedState.value = CurrencyDropdownState.CURRENT
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterScreenPreview() {
//    ConverterScreen(viewModel)
}

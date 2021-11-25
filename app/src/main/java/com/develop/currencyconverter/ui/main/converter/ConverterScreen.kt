package com.develop.currencyconverter.ui.main.converter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.components.DropdownCurrencyList
import com.develop.currencyconverter.ui.main.MainViewModel
import kotlinx.coroutines.launch


/*
 * Created by Arafin Mahtab on 9/22/2021.
 */

private const val DEFAULT_BASE_CURRENCY_VALUE = 1.0
private const val DEFAULT_CURRENT_CURRENCY_VALUE = 0.0

private const val DEFAULT_BASE_CURRENCY = "USD"
private const val DEFAULT_CURRENT_CURRENCY = "BDT"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConverterScreen(viewModel: MainViewModel) {
    val currencyList by viewModel.liveCurrencyWithFlagList.collectAsState()

    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val baseCurrency = remember { mutableStateOf(DEFAULT_BASE_CURRENCY) }
    val baseCurrencyAmount = remember { mutableStateOf(DEFAULT_BASE_CURRENCY_VALUE.toString()) }

    val currentCurrency = remember { mutableStateOf(DEFAULT_CURRENT_CURRENCY) }
    val currentCurrencyAmount = remember { mutableStateOf(DEFAULT_CURRENT_CURRENCY_VALUE.toString()) }

    val currencyDropdownSelectedState = remember { mutableStateOf(CurrencyDropdownState.NONE) }

    val baseCurrencyAmountSelector: (String) -> Unit = {
        baseCurrencyAmount.value = it
        viewModel.convertCurrency(baseCurrency.value, baseCurrencyAmount.value, currentCurrency.value) { convertedCurrencyAmount ->
            currentCurrencyAmount.value = convertedCurrencyAmount
        }
    }

    val currentCurrencyAmountSelector: (String) -> Unit = {
        currentCurrencyAmount.value = it
        viewModel.convertCurrency(currentCurrency.value, currentCurrencyAmount.value, baseCurrency.value) { convertedCurrencyValue ->
            baseCurrencyAmount.value = convertedCurrencyValue
        }
    }

    baseCurrencyAmountSelector(baseCurrencyAmount.value)

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            Box(modifier = Modifier.defaultMinSize(minHeight = 1.dp)) {
                DropdownCurrencyList(currencies = currencyList) {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                        when (currencyDropdownSelectedState.value) {
                            CurrencyDropdownState.BASE -> {
                                baseCurrency.value = it
                                baseCurrencyAmountSelector(baseCurrencyAmount.value)
                            }
                            CurrencyDropdownState.CURRENT -> {
                                currentCurrency.value = it
                                currentCurrencyAmountSelector(currentCurrencyAmount.value)
                            }
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
                viewModel.getCurrencyWithFlag(baseCurrency.value),
                baseCurrencyAmount,
                baseCurrencyAmountSelector
            ) {
                currencyDropdownSelectedState.value = CurrencyDropdownState.BASE
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            val tempBaseCurrency = baseCurrency.value
                            baseCurrency.value = currentCurrency.value
                            currentCurrency.value = tempBaseCurrency

                            val tempBaseCurrencyAmount = baseCurrencyAmount.value
                            baseCurrencyAmount.value = currentCurrencyAmount.value
                            currentCurrencyAmount.value = tempBaseCurrencyAmount
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
                viewModel.getCurrencyWithFlag(currentCurrency.value),
                currentCurrencyAmount,
                currentCurrencyAmountSelector
            ) {
                currencyDropdownSelectedState.value = CurrencyDropdownState.CURRENT
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterScreenPreview() {
//    ConverterScreen(viewModel)
}

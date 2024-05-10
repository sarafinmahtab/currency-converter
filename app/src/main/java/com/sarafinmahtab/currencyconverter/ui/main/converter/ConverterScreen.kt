package com.sarafinmahtab.currencyconverter.ui.main.converter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarafinmahtab.currencyconverter.R
import com.sarafinmahtab.currencyconverter.ui.components.ShimmerAnimation
import com.sarafinmahtab.currencyconverter.ui.components.ShimmerType
import com.sarafinmahtab.currencyconverter.ui.main.CurrencyRatesUIState
import com.sarafinmahtab.currencyconverter.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*
 * Created by Arafin Mahtab on 9/22/2021.
 */

const val DEFAULT_BASE_CURRENCY_VALUE = 1.0
const val DEFAULT_CURRENT_CURRENCY_VALUE = 0.0

const val DEFAULT_BASE_CURRENCY = "USD"
const val DEFAULT_CURRENT_CURRENCY = "BDT"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(viewModel: MainViewModel) {
    val currencyRatesUIState by viewModel.currencyRatesUIState.collectAsState()

    val converterState = rememberConverterState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

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

    when (currencyRatesUIState) {
        CurrencyRatesUIState.Idle -> {}
        CurrencyRatesUIState.Loading -> {
            ShimmerConverterContent(
                baseCountryCode = converterState.baseCurrency.value,
                currentCountryCode = converterState.currentCurrency.value,
            )
        }

        is CurrencyRatesUIState.Success -> {
            ConverterContent(
                scope = scope,
                bottomSheetState = sheetState,
                converterState = converterState,
                baseCountryFlag = viewModel.getCurrencyFlag(converterState.baseCurrency.value),
                currentCountryFlag = viewModel.getCurrencyFlag(converterState.currentCurrency.value),
                onClickBaseCurrencyPicker = {
                    converterState.currencyDropdownSelectedState.value = CurrencyDropdownState.BASE
                    showBottomSheet = true
                },
                baseCurrencyAmountSelector = baseCurrencyAmountSelector,
                onClickCurrentCurrencyPicker = {
                    converterState.currencyDropdownSelectedState.value =
                        CurrencyDropdownState.CURRENT
                    showBottomSheet = true
                },
                currentCurrencyAmountSelector = currentCurrencyAmountSelector,
                onClickCurrencySwap = {
                    with(converterState) {
                        val tempBaseCurrency = baseCurrency.value
                        baseCurrency.value = currentCurrency.value
                        currentCurrency.value = tempBaseCurrency

                        baseCurrencyAmountSelector(baseCurrencyAmount.value)
                    }
                },
            )

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                    sheetState = sheetState
                ) {
                    Column {
                        Box(modifier = Modifier.defaultMinSize(minHeight = 1.dp)) {
                            DropdownCurrencyList(
                                currencies = viewModel.countryCurrencyList,
                                onSelectCurrency = {
                                    scope.launch {
                                        sheetState.hide()
                                        with(converterState) {
                                            when (currencyDropdownSelectedState.value) {
                                                CurrencyDropdownState.BASE -> {
                                                    baseCurrency.value = it.code
                                                }

                                                CurrencyDropdownState.CURRENT -> {
                                                    currentCurrency.value = it.code
                                                }
                                            }
                                            baseCurrencyAmountSelector(baseCurrencyAmount.value)
                                        }
                                    }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        is CurrencyRatesUIState.Failure -> {}
    }
}

@Composable
fun ShimmerConverterContent(
    baseCountryCode: String,
    currentCountryCode: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.padding(25.dp))
        ShimmerAnimation(
            shimmerType = ShimmerType.CurrencyPickerWithAmount(
                titleRes = R.string.from_currency,
                code = baseCountryCode,
            )
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_swap_currency),
                    contentDescription = stringResource(R.string.swap_currency),
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                    text = stringResource(id = R.string.swap_currency),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        ShimmerAnimation(
            shimmerType = ShimmerType.CurrencyPickerWithAmount(
                titleRes = R.string.from_currency,
                code = currentCountryCode,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterContent(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    converterState: ConverterState,
    baseCountryFlag: String,
    currentCountryFlag: String,
    onClickBaseCurrencyPicker: () -> Unit,
    baseCurrencyAmountSelector: (String) -> Unit,
    onClickCurrentCurrencyPicker: () -> Unit,
    currentCurrencyAmountSelector: (String) -> Unit,
    onClickCurrencySwap: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.padding(25.dp))
        CurrencyPickerWithAmount(
            coroutineScope = scope,
            bottomSheetState = bottomSheetState,
            titleRes = R.string.from_currency,
            currencyCode = converterState.baseCurrency.value,
            countryFlag = baseCountryFlag,
            currencyAmount = converterState.baseCurrencyAmount,
            currencyAmountSelector = baseCurrencyAmountSelector,
            onClickCurrencyPicker = onClickBaseCurrencyPicker,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable(onClick = onClickCurrencySwap)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_swap_currency),
                    contentDescription = stringResource(R.string.swap_currency),
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                    text = stringResource(id = R.string.swap_currency),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        CurrencyPickerWithAmount(
            coroutineScope = scope,
            bottomSheetState = bottomSheetState,
            titleRes = R.string.from_currency,
            currencyCode = converterState.currentCurrency.value,
            countryFlag = currentCountryFlag,
            currencyAmount = converterState.currentCurrencyAmount,
            currencyAmountSelector = currentCurrencyAmountSelector,
            onClickCurrencyPicker = onClickCurrentCurrencyPicker,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ConverterContentPreview() {
    ConverterContent(
        scope = rememberCoroutineScope(),
        bottomSheetState = rememberModalBottomSheetState(),
        converterState = rememberConverterState(),
        baseCountryFlag = "",
        currentCountryFlag = "",
        onClickBaseCurrencyPicker = {},
        baseCurrencyAmountSelector = {},
        onClickCurrentCurrencyPicker = {},
        currentCurrencyAmountSelector = {},
        onClickCurrencySwap = {}
    )
}

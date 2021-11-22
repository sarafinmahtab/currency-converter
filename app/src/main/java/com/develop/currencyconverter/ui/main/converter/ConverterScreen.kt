package com.develop.currencyconverter.ui.main.converter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.components.CurrencyList
import com.develop.currencyconverter.ui.components.CurrencySelector
import com.develop.currencyconverter.ui.components.appTextFieldColors
import com.develop.currencyconverter.ui.main.MainViewModel
import kotlinx.coroutines.launch


/*
 * Created by Arafin Mahtab on 9/22/2021.
 * Copyright (c) 2021 Rangan Apps. All rights reserved.
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConverterScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currencyList by viewModel.liveCurrencyList.collectAsState()

    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val baseCurrency = remember { mutableStateOf(context.getString(R.string.title_currency_picker)) }
    val baseCurrencyValue = remember { mutableStateOf("1.0") }
    val baseCurrencyValueSelector: (String) -> Unit = { baseCurrencyValue.value = it }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            Box(modifier = Modifier.defaultMinSize(minHeight = 1.dp)) {
                CurrencyList(currencies = currencyList) {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                        baseCurrency.value = it
                    }
                }
            }
        },
        sheetShape = RoundedCornerShape(topEnd = medium, topStart = medium)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f),
                    value = baseCurrencyValue.value,
                    label = {
                        Text(
                            text = stringResource(R.string.base_currency),
                            color = MaterialTheme.colors.secondary,
                            style = MaterialTheme.typography.caption
                        )
                    },
                    singleLine = true,
                    onValueChange = baseCurrencyValueSelector,
                    colors = appTextFieldColors()
                )

                CurrencySelector(
                    modifier = Modifier.weight(1f),
                    currencyValue = baseCurrency.value
                ) {
                    coroutineScope.launch {
                        if (modalBottomSheetState.isVisible) {
                            modalBottomSheetState.hide()
                        } else {
                            modalBottomSheetState.show()
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(value = "1", onValueChange = {})
            }
            Text(text = "Demo")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterScreenPreview() {
//    ConverterScreen(viewModel)
}

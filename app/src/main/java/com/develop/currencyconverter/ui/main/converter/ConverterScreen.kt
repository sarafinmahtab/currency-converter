package com.develop.currencyconverter.ui.main.converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.components.ItemSelectionSpinner
import com.develop.currencyconverter.ui.main.MainViewModel


/*
 * Created by Arafin Mahtab on 9/22/2021.
 * Copyright (c) 2021 Rangan Apps. All rights reserved.
 */

@Composable
fun ConverterScreen(viewModel: MainViewModel) {
    val currencyList by viewModel.liveCurrencyList.collectAsState()
    val defaultFromCurrency = 1.1f

    Column {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                value = defaultFromCurrency.toString(),
                label = {
                    Text(text = stringResource(R.string.from_currency))
                },
                singleLine = true,
                onValueChange = {
                    //TODO work on value change
                }
            )
            ItemSelectionSpinner(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                items = currencyList,
                initialSelection = "Select Currency"
            )
        }
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(value = "1", onValueChange = {})
        }
        Text(text = "Demo")
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterScreenPreview() {
//    ConverterScreen(viewModel)
}

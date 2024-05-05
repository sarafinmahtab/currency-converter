package com.sarafinmahtab.currencyconverter.ui.main.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sarafinmahtab.currencyconverter.ui.main.MainViewModel
import com.sarafinmahtab.currencyconverter.ui.main.converter.CurrencyItem


/*
 * Created by Arafin Mahtab on 9/22/2021.
 */

@Composable
fun CurrenciesScreen(viewModel: MainViewModel) {
    val liveCountryCurrencyList by viewModel.liveCountryCurrencies.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .wrapContentSize(Alignment.Center)
    ) {
        LazyColumn {
            itemsIndexed(liveCountryCurrencyList) { index, countryCurrency ->
                CurrencyItem(
                    currency = countryCurrency,
                    onSelectCurrency = {},
                    showDivider = index < liveCountryCurrencyList.lastIndex,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrenciesScreenPreview() {
//    CurrenciesScreen(mainViewModel)
}

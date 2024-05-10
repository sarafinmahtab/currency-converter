package com.sarafinmahtab.currencyconverter.ui.main.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sarafinmahtab.currencyconverter.ui.main.MainViewModel
import com.sarafinmahtab.currencyconverter.ui.main.converter.CurrencyItem


/*
 * Created by Arafin Mahtab on 9/22/2021.
 */

@Composable
fun CurrenciesScreen(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .wrapContentSize(Alignment.Center)
    ) {
        LazyColumn {
            itemsIndexed(viewModel.countryCurrencyList) { index, countryCurrency ->
                CurrencyItem(
                    currency = countryCurrency,
                    onSelectCurrency = {},
                    showDivider = index < viewModel.countryCurrencyList.lastIndex,
                )
            }
        }
    }
}

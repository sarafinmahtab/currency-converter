package com.develop.currencyconverter.ui.main.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develop.currencyconverter.ui.main.MainViewModel
import com.develop.currencyconverter.ui.theme.stroke


/*
 * Created by Arafin Mahtab on 9/22/2021.
 */

@Composable
fun CurrenciesScreen(viewModel: MainViewModel) {
    val liveCurrencyWithFlagList by viewModel.liveCurrencyWithFlagList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .wrapContentSize(Alignment.Center)
    ) {
        LazyColumn {
            items(liveCurrencyWithFlagList) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* TODO */ }
                        .padding(),
                    text = it,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelMedium,
                )
                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrenciesScreenPreview() {
//    CurrenciesScreen(mainViewModel)
}

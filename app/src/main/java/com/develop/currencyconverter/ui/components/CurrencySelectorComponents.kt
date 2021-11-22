package com.develop.currencyconverter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develop.currencyconverter.R


/*
 * Created by Arafin Mahtab on 11/22/2021.
 * Copyright (c) 2021 Rangan Apps. All rights reserved.
 */

@Composable
fun CurrencyList(
    currencies: List<String>,
    selectCurrency: (String) -> Unit
) {
    LazyColumn {
        items(currencies) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        selectCurrency(it)
                    },
                text = it,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun CurrencySelector(
    modifier: Modifier = Modifier,
    currencyValue: String,
    onClickSelector: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onClickSelector),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = currencyValue,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.button
        )
        Icon(
            modifier = Modifier.padding(start = 8.dp),
            painter = painterResource(id = R.drawable.ic_spinner_arrow),
            contentDescription = currencyValue
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencySelectorPreview() {
    CurrencySelector(currencyValue = "Select") {}
}

package com.develop.currencyconverter.ui.main.converter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


/*
 * Created by Arafin Mahtab on 9/22/2021.
 * Copyright (c) 2021 Rangan Apps. All rights reserved.
 */

@Composable
fun ConverterScreen() {
    Row {
        Column {
            TextField(value = "1", onValueChange = {})
        }
        Column {
            TextField(value = "1", onValueChange = {})
        }
        Text(text = "Demo")
    }
}


@Preview(showBackground = true)
@Composable
fun ConverterScreenPreview() {
    ConverterScreen()
}

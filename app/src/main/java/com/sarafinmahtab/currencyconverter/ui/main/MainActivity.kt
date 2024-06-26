package com.sarafinmahtab.currencyconverter.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sarafinmahtab.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchLatestCurrencyRates()
        viewModel.loadCountriesInfo(resources)

        setContent {
            CurrencyConverterTheme {
                MainScreen(viewModel)
            }
        }
    }
}

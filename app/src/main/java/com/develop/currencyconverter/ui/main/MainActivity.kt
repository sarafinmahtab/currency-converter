package com.develop.currencyconverter.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.android.core.helpers.Results
import com.develop.currencyconverter.R
import com.develop.currencyconverter.model.CurrencyConversionResult
import com.develop.currencyconverter.ui.theme.CurrencyConverterTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                CurrencyConverterHomePage(viewModel)
            }
        }

        viewModel.convertCurrency("USD", "BDT", 1.0)
    }
}

private fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@Composable
fun CurrencyConverterHomePage(viewModel: MainViewModel) {
    val context = LocalContext.current
    val convertedCurrencyState = viewModel.convertedCurrencyResult.observeAsState()

    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val statusBarColor = MaterialTheme.colors.primaryVariant

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )

        // setNavigationBarsColor() also exist
    }

    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.h1
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calculater),
                        contentDescription = null, // decorative element
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(Dp(5f))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = Dp(24f)
                                ), // You can also change the color and radius of the ripple
                            ) {
                                showToastMessage(context, "Hello Compose Click")
                            }
                    )
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (convertedCurrencyState.value != null) {
                    GreetingText(context, convertedCurrencyState.value!!)
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.sizeIn(
                            maxWidth = Dp(30f),
                            maxHeight = Dp(30f)
                        ),
                        color = MaterialTheme.colors.secondaryVariant,
                        strokeWidth = Dp(2.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingText(context: Context, currencyConversionResult: Results<CurrencyConversionResult>) {
    when (currencyConversionResult) {
        is Results.Failure -> {
            showToastMessage(context, currencyConversionResult.throwable.toString())
        }
        is Results.Success -> {
            val convertedCurrencyResponse: String = Gson().toJson(currencyConversionResult.value)
            Text(
                text = convertedCurrencyResponse,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyConverterTheme {
//        CurrencyConverterHomePage()
    }
}

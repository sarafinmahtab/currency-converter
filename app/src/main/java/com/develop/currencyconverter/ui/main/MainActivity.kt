package com.develop.currencyconverter.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                CurrencyConverterHomePage()
            }
        }
    }
}

private fun showMessage(context: Context) {
    Toast.makeText(
        context,
        "Hello Compose Click",
        Toast.LENGTH_LONG
    ).show()
}

@Composable
fun CurrencyConverterHomePage() {
    val context = LocalContext.current
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colors.onPrimary
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
                            indication = rememberRipple(bounded = false, radius = Dp(24f)), // You can also change the color and radius of the ripple
                        ) {
                            showMessage(context)
                        }
                    )
                }
            )
            GreetingText("Compose World")
        }
    }
}

@Composable
fun GreetingText(name: String) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyConverterTheme {
        CurrencyConverterHomePage()
    }
}

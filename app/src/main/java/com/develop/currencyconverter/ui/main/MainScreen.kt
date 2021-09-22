package com.develop.currencyconverter.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.rememberNavController
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.theme.CurrencyConverterTheme
import com.google.accompanist.insets.ProvideWindowInsets


/*
 * Created by Arafin Mahtab on 9/22/2021.
 * Copyright (c) 2021 Rangan Apps. All rights reserved.
 */

@Composable
fun MainScreen() {
    val context = LocalContext.current

//    val viewModel = viewModel(MainViewModel::class.java)
//    val convertedCurrencyState = viewModel.convertedCurrencyResult.collectAsState()

    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Navigation(navController = navController, innerPadding)
    }
}

@Composable
fun TopBar() {
    val context = LocalContext.current

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
                imageVector = Icons.Outlined.Settings,
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
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyConverterTheme {
        ProvideWindowInsets {
            MainScreen()
        }
    }
}

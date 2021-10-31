package com.develop.currencyconverter.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.develop.currencyconverter.R


/*
 * Created by Arafin Mahtab on 9/22/2021.
 * Copyright (c) 2021 Rangan Apps. All rights reserved.
 */

@Composable
fun MainScreen() {
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
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1
            )
        },
        backgroundColor = MaterialTheme.colors.onPrimary,
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_options),
                contentDescription = stringResource(id = R.string.settings),
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(Dp(5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 24.dp
                        )
                    ) {
                        /* TODO */
                    }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

package com.sarafinmahtab.currencyconverter.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarafinmahtab.currencyconverter.R
import com.sarafinmahtab.currencyconverter.ui.main.converter.ConverterScreen
import com.sarafinmahtab.currencyconverter.ui.main.currencies.CurrenciesScreen
import com.sarafinmahtab.currencyconverter.ui.main.timeline.TimelineScreen


/*
 * Created by Arafin Mahtab on 9/22/2021.
 */

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Converter.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(BottomNavItem.Converter.route) {
                ConverterScreen(viewModel)
            }
            composable(BottomNavItem.Currencies.route) {
                CurrenciesScreen(viewModel)
            }
            composable(BottomNavItem.Timeline.route) {
                TimelineScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(id = R.string.more_menu),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(Dp(5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 24.dp
                        ),
                        onClick = { /* TODO */ }
                    )
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

package com.develop.currencyconverter.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.main.converter.ConverterScreen
import com.develop.currencyconverter.ui.main.currencies.CurrenciesScreen
import com.develop.currencyconverter.ui.main.timeline.TimelineScreen


/*
 * Created by Arafin Mahtab on 9/19/2021.
 * Copyright (c) 2021 Rangan Apps. All rights reserved.
 */

sealed class BottomNavigationScreen(val route: String, @DrawableRes val icon: Int, @StringRes val title: Int) {
    object Converter : BottomNavigationScreen("converter", R.drawable.ic_converter, R.string.converter)
    object Currencies : BottomNavigationScreen("currencies", R.drawable.ic_currency, R.string.currencies)
    object Timeline : BottomNavigationScreen("timeline", R.drawable.ic_timeline, R.string.timeline)
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavigationScreen.Converter,
        BottomNavigationScreen.Currencies,
        BottomNavigationScreen.Timeline
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = stringResource(id = screen.title)
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.primary.copy(0.3f),
                modifier = Modifier.background(color = MaterialTheme.colors.onPrimary),
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun Navigation(mainViewModel: MainViewModel, navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController,
        startDestination = BottomNavigationScreen.Converter.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(BottomNavigationScreen.Converter.route) {
            ConverterScreen(mainViewModel)
        }
        composable(BottomNavigationScreen.Currencies.route) {
            CurrenciesScreen()
        }
        composable(BottomNavigationScreen.Timeline.route) {
            TimelineScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavBar(navController = NavController(LocalContext.current))
}

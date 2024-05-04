package com.develop.currencyconverter.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.develop.currencyconverter.R
import com.google.android.material.bottomnavigation.BottomNavigationView


/*
 * Created by Arafin Mahtab on 9/19/2021.
 */

sealed class BottomNavItem(
    val route: String,
    @DrawableRes
    val iconRes: Int,
    @StringRes
    val labelRes: Int,
) {
    data object Converter : BottomNavItem(
        route = "converter",
        iconRes = R.drawable.ic_currency_exchange,
        labelRes = R.string.converter
    )

    data object Currencies : BottomNavItem(
        route = "currencies",
        iconRes = R.drawable.ic_currency,
        labelRes = R.string.currencies
    )

    data object Timeline : BottomNavItem(
        route = "timeline",
        iconRes = R.drawable.ic_timeline,
        labelRes = R.string.timeline
    )
}

data class BottomNavScreen(
    @StringRes val labelRes: Int = 0,
    @DrawableRes val iconRes: Int = 0,
    val route: String = "",
)

fun bottomNavigationScreens(): List<BottomNavScreen> {
    return listOf(
        BottomNavScreen(
            route = BottomNavItem.Converter.route,
            iconRes = BottomNavItem.Converter.iconRes,
            labelRes = BottomNavItem.Converter.labelRes,
        ),
        BottomNavScreen(
            route = BottomNavItem.Currencies.route,
            iconRes = BottomNavItem.Currencies.iconRes,
            labelRes = BottomNavItem.Currencies.labelRes,
        ),
        BottomNavScreen(
            route = BottomNavItem.Timeline.route,
            iconRes = BottomNavItem.Timeline.iconRes,
            labelRes = BottomNavItem.Timeline.labelRes,
        ),
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }
    NavigationBar(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
    ) {
        bottomNavigationScreens().forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.iconRes),
                        contentDescription = stringResource(id = screen.labelRes),
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary.copy(0.3f),
                ),
                selected = index == navigationSelectedItem,
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = NavController(LocalContext.current))
}

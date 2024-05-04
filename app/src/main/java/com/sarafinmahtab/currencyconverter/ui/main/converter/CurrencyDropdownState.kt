package com.sarafinmahtab.currencyconverter.ui.main.converter

import androidx.annotation.IntDef

/*
 * Created by Arafin Mahtab on 12/23/2021.
 */

@IntDef(
    CurrencyDropdownState.NONE,
    CurrencyDropdownState.BASE,
    CurrencyDropdownState.CURRENT
)
annotation class CurrencyDropdownState {
    companion object {
        const val NONE = -1
        const val BASE = 0
        const val CURRENT = 1
    }
}

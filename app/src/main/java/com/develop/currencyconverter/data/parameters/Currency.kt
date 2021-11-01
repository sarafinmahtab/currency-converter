package com.develop.currencyconverter.data.parameters

import androidx.annotation.StringDef

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

@StringDef(
    Currency.USD,
    Currency.EUR,
    Currency.BDT
)
annotation class Currency {
    companion object {
        const val USD = "USD"
        const val EUR = "EUR"
        const val BDT = "BDT"
    }
}

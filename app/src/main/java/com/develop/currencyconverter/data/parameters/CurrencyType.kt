package com.develop.currencyconverter.data.parameters

import androidx.annotation.StringDef

/**
 * @author Shamsul Arafin Mahtab
 * @since 22/06/21
 */

@StringDef(
    CurrencyType.USD,
    CurrencyType.EUR,
    CurrencyType.BDT
)
annotation class CurrencyType {
    companion object {
        const val USD = "USD"
        const val EUR = "EUR"
        const val BDT = "BDT"
    }
}

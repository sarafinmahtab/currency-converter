package com.develop.currencyconverter.data.model.local

import com.google.gson.annotations.SerializedName


/*
 * Created by Arafin Mahtab on 11/10/2021.
 */

data class Currency(
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)

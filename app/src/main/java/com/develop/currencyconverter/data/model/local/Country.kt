package com.develop.currencyconverter.data.model.local

import com.google.gson.annotations.SerializedName


/*
 * Created by Arafin Mahtab on 11/10/2021.
 */

data class Country(
    @SerializedName("cca2")
    val countryCode: String,
    @SerializedName("currencies")
    val currencies: Map<String, Currency>,
    @SerializedName("flag")
    val flag: String
)

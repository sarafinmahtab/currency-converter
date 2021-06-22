package com.develop.currencyconverter.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    @SerializedName("currency_name")
    @Expose
    val currencyName: String? = null,
    @SerializedName("rate")
    @Expose
    val rate: String? = null,
    @SerializedName("rate_for_amount")
    @Expose
    val rateForAmount: String? = null
) : Parcelable
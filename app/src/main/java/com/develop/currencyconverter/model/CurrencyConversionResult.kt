package com.develop.currencyconverter.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyConversionResult(
    @SerializedName("amount")
    @Expose
    val amount: String? = null,
    @SerializedName("base_currency_code")
    @Expose
    val baseCurrencyCode: String? = null,
    @SerializedName("base_currency_name")
    @Expose
    val baseCurrencyName: String? = null,
    @SerializedName("rates")
    @Expose
    val rates: HashMap<String, Currency>? = null,
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("updated_date")
    @Expose
    val updatedDate: String? = null
) : Parcelable

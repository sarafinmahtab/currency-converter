package com.develop.currencyconverter.data.model

import android.os.Parcelable
import com.develop.currencyconverter.data.AppConstant.defaultAmount
import com.develop.currencyconverter.data.AppConstant.defaultBaseCurrencyCode
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyConversionResult(
    @SerializedName("amount")
    @Expose
    val amount: String = defaultAmount,
    @SerializedName("base_currency_code")
    @Expose
    val baseCurrencyCode: String = defaultBaseCurrencyCode,
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

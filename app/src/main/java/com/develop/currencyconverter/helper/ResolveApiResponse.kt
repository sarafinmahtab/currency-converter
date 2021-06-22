package com.develop.currencyconverter.helper

import android.util.Log
import com.android.core.helpers.Results
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/21
 */

@Singleton
class ResolveApiResponse @Inject constructor() {

    suspend fun <T> resolve(classTag: String, updateCall: suspend () -> T) =
        try {
            Results.Success(updateCall.invoke())
        } catch (throwable: Throwable) {
            Log.w(classTag, throwable)
            throwable.printStackTrace()
            Results.Failure(throwable)
        }
}

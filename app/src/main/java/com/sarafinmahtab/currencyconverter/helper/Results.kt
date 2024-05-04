package com.android.core.helpers

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/2021
 */

sealed class Results<out T> {
    data class Success<out T>(val value: T) : Results<T>()
    data class Failure(val throwable: Throwable) : Results<Nothing>()
}

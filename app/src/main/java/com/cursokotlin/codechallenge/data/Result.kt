package com.cursokotlin.codechallenge.data

import com.cursokotlin.codechallenge.data.internal.ServerError

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val error: ServerError, val t: Throwable? = null) : Result<Nothing>()

    data class Loading(val isLoading: Boolean) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Errors[server errors=$error, throwable=$t]"
            is Loading -> "Loading[isLoading = $isLoading]"
        }
    }
}
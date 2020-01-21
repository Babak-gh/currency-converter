package com.babak.rates.data

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) : Result<Nothing>()
    object NetworkError : Result<Nothing>()

}
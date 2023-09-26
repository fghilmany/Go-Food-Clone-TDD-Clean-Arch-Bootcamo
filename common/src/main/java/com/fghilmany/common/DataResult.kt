package com.fghilmany.common

sealed class DataResult<T> {
    data class Success<T>(val data: T) : DataResult<T>()
    data class Failure<T>(val errorMessage: String) : DataResult<T>()
}
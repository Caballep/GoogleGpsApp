package com.example.googlegpsapp.core

sealed class Result<T>(val data: T? = null, val errorType: ErrorType? = null) {
    class Loading<T>(data: T? = null) : Result<T>(data)
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(errorType: ErrorType, data: T? = null) :
        Result<T>(errorType = errorType, data = data)
}

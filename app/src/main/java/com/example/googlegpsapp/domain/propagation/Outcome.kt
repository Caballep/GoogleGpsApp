package com.example.googlegpsapp.domain.propagation

sealed class Outcome<out T> {
    data class Success<out T>(val data: T) : Outcome<T>()
    data class Error(val errorType: ErrorType) : Outcome<Nothing>()
}

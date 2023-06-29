package com.example.googlegpsapp.domain.util

sealed class Outcome<out T> {
    data class Success<out T>(val data: T) : Outcome<T>()
    data class Error(val errorType: ErrorType) : Outcome<Nothing>()
}

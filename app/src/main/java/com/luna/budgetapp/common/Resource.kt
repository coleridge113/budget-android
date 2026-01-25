package com.luna.budgetapp.common

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Error<out T>(val message: String, val data: T? = null) : Resource<T>
    data object Loading : Resource<Nothing>
}

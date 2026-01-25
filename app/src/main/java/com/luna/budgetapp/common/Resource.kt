package com.luna.budgetapp.common

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Error(val message: String, val data: Any? = null) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}

package com.luna.budgetapp.network

data class Response<T>(
    val body: T?,
    val statusCode: Int,
    val statusMessage: String
)
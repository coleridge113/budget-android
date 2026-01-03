package com.luna.budgetapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    val username: String, 
    val password: String
) 

data class AuthResponse(
    @SerializedName("accessToken")
    val token: String,
    @SerializedName("tokenType")
    val type: String
)

package com.luna.budgetapp.data.remote.dto

data class AuthRequest(
    val username: String, 
    val password: String
) 

data class AuthResponse(val token: String)

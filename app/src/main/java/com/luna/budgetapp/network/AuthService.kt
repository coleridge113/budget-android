package com.luna.budgetapp.network

import com.luna.budgetapp.data.remote.dto.AuthRequest
import com.luna.budgetapp.data.remote.dto.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    
    @POST("api/v1/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

}

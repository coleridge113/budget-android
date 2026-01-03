package com.luna.budgetapp.data.remote.source

import com.luna.budgetapp.data.remote.dto.AuthRequest
import com.luna.budgetapp.data.remote.dto.AuthResponse
import com.luna.budgetapp.network.AuthService

class AuthRemoteDataSource(
    private val api: AuthService
) {
    suspend fun fetchToken(req: AuthRequest): AuthResponse {
        return api.fetchToken(req)
    }
}

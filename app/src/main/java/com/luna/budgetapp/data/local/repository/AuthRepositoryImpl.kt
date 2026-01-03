package com.luna.budgetapp.data.local.repository

import android.util.Log
import com.luna.budgetapp.data.datastore.AuthLocalDataSource
import com.luna.budgetapp.data.remote.dto.AuthRequest
import com.luna.budgetapp.data.remote.source.AuthRemoteDataSource
import com.luna.budgetapp.domain.repository.AuthRepository

import com.luna.budgetapp.data.utils.JwtUtils

class AuthRepositoryImpl(
    private val local: AuthLocalDataSource,
    private val remote: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun refreshJwtToken(): String {
        val request = AuthRequest("guest", "password")
        val token = remote.fetchToken(request).token
        Log.d("JWToken", "Token: $token")
        local.saveJwtToken(token)
        return token
    }

    override suspend fun fetchJwtToken(): String? {
        val stored = local.getJwtToken()
        return if (stored.isNullOrEmpty() || JwtUtils.isJwtTokenExpired(stored)) {
            refreshJwtToken()
        } else {
            stored
        }
    }
}

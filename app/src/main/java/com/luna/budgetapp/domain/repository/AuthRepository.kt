package com.luna.budgetapp.domain.repository

interface AuthRepository {

    suspend fun refreshJwtToken(): String 

    suspend fun fetchJwtToken(): String?

}

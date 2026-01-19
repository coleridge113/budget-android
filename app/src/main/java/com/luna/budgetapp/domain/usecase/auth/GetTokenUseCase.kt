package com.luna.budgetapp.domain.usecase.auth

import com.luna.budgetapp.domain.repository.AuthRepository

class GetTokenUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): String {
        return repository.fetchJwtToken() ?: throw IllegalStateException("Empty token")
    }
}

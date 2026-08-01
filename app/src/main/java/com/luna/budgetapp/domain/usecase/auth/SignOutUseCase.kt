package com.luna.budgetapp.domain.usecase.auth

import com.google.firebase.auth.FirebaseAuth
import com.luna.budgetapp.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignOutUseCase(
    private val db: AppDatabase
) {
    suspend operator fun invoke(
        auth: FirebaseAuth
    ) {
        auth.signOut()
        withContext(Dispatchers.IO) {
            db.clearAllTables()
        }
    }
}

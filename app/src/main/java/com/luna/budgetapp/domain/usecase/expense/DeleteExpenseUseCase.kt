package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.local.repository.Repository
import com.luna.budgetapp.data.remote.ExpenseRemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteExpenseUseCase(
    private val repository: Repository,
    private val remoteSource: ExpenseRemoteSource
) {
    operator fun invoke(id: Long): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            try {
                repository.deleteExpense(id)
                remoteSource.deleteExpense(id)
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
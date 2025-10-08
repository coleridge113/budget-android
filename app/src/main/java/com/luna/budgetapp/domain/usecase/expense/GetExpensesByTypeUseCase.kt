package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.local.repository.Repository
import com.luna.budgetapp.data.mapper.fromCacheToEntity
import com.luna.budgetapp.data.mapper.fromDtoToEntity
import com.luna.budgetapp.data.mapper.toExpense
import com.luna.budgetapp.data.mapper.toExpenseCache
import com.luna.budgetapp.data.remote.ExpenseRemoteSource
import com.luna.budgetapp.domain.model.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetExpensesByTypeUseCase(
    private val repository: Repository,
    private val remoteSource: ExpenseRemoteSource
) {
    operator fun invoke(type: String): Flow<Resource<List<Expense>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val local = repository.getExpensesByCategory(type)
                if (local.isNotEmpty()) {
                    emit(Resource.Success(local.fromCacheToEntity()))
                } else {
                    val remote = remoteSource.getExpensesByCategory(type)
                    repository.addExpenses(remote.toExpenseCache())
                    emit(Resource.Success(remote.fromDtoToEntity()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
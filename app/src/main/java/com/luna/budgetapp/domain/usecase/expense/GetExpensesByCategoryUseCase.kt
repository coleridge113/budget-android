package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.local.repository.Repository
import com.luna.budgetapp.data.mapper.toExpense
import com.luna.budgetapp.data.mapper.toExpenseCache
import com.luna.budgetapp.data.remote.ExpenseRemoteSource
import com.luna.budgetapp.domain.model.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetExpensesByCategoryUseCase(
    private val repository: Repository,
    private val remoteSource: ExpenseRemoteSource
) {
    operator fun invoke(category: String): Flow<Resource<List<Expense>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val local = repository.getExpensesByCategory(category)
                if (local.isNotEmpty()) {
                    emit(Resource.Success(local.toExpense()))
                } else {
                    val remote = remoteSource.getExpensesByCategory(category)
                    repository.addExpenses(remote.toExpenseCache())
                    emit(Resource.Success(remote.toExpense()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
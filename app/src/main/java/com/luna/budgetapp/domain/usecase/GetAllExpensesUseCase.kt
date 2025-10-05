package com.luna.budgetapp.domain.usecase

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.local.repository.RepositoryImpl
import com.luna.budgetapp.data.mapper.toExpense
import com.luna.budgetapp.data.mapper.toExpenseCache
import com.luna.budgetapp.data.remote.ExpenseRemoteSource
import com.luna.budgetapp.domain.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllExpensesUseCase(
    private val repository: RepositoryImpl,
    private val remoteSource: ExpenseRemoteSource
) {
    operator fun invoke(): Flow<Resource<List<Expense>>> = flow {
        emit(Resource.Loading())
        try {
            val local = repository.getAllExpenses()
            if (local.isNotEmpty()) {
                emit(Resource.Success(local.map { it.toExpense() }))
            } else {
                val remote = remoteSource.getAllExpenses()
                remote.forEach { repository.addExpense(it.toExpenseCache()) }
                emit(Resource.Success(remote.map { it.toExpense() }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}
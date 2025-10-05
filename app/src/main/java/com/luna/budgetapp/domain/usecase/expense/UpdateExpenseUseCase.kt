package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.local.repository.Repository
import com.luna.budgetapp.data.mapper.toExpense
import com.luna.budgetapp.data.mapper.toExpenseCache
import com.luna.budgetapp.data.mapper.toExpenseDto
import com.luna.budgetapp.data.remote.ExpenseRemoteSource
import com.luna.budgetapp.domain.model.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateExpenseUseCase(
    private val repository: Repository,
    private val remoteSource: ExpenseRemoteSource
) {
    operator fun invoke(expense: Expense, id: Long): Flow<Resource<Expense>> {
        return flow {
            emit(Resource.Loading())
            try {
                val localResult = repository.updateExpense(expense.toExpenseCache(), id)
                remoteSource.updateExpense(localResult.toExpenseDto(), id)
                emit(Resource.Success(localResult.toExpense()))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
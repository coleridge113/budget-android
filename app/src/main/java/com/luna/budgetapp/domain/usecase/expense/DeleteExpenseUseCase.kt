package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.domain.model.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteExpenseUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(expense: Expense): Flow<Resource<Expense>> {
        return flow {
            emit(Resource.Loading())
            try {
                repository.deleteExpense(expense)
                emit(Resource.Success(expense))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }
}

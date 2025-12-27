package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllExpensesUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<Resource<List<Expense>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val expenses = repository.getAllExpenses()
                emit(Resource.Success(expenses))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }
}

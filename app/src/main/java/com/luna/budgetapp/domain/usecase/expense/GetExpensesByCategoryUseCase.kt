package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetExpensesByCategoryUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(category: String): Flow<Resource<List<Expense>>> {
        return flow {
            repository.getExpensesByCategory(category).collect { resource ->
                when(resource) {
                    is Resource.Loading -> {
                        emit(Resource.Loading())
                    }
                    is Resource.Error -> {
                        emit(Resource.Error(resource.message ?: "Something went wrong..."))
                    }
                    is Resource.Success -> {
                        emit(Resource.Success(resource.data ?: emptyList()))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}

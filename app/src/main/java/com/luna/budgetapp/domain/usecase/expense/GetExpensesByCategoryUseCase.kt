package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetExpensesByCategoryUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(category: String): Flow<Resource<List<Expense>>> {
        return repository.getExpensesByCategory(category)
    } 
}

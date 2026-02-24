package com.luna.budgetapp.domain.usecase.expense

import androidx.paging.PagingData
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetAllExpensesUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<PagingData<Expense>> {
        return repository.getAllExpenses()  
    }
}

package com.luna.budgetapp.domain.usecase.expense

import java.time.LocalDateTime
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.domain.model.Expense
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.Flow

class GetExpensesByDateRangeUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<Expense>> {
        return repository.getExpensesByDateRange(start, end)
    }
}

package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.domain.model.CategoryTotalProjection
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow

class GetCategoryTotalsByDateRange(
    private val repository: ExpenseRepository
) {
    operator fun invoke(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<CategoryTotalProjection>> {
        return repository.getCategoryTotalsByDateRange(start, end)
    }
}

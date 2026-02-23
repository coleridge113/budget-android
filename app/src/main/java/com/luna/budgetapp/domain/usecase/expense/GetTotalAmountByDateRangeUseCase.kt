package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class GetTotalAmountByDateRangeUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<Double> {
        return repository.getTotalAmountByDateRange(start, end)
    }
}

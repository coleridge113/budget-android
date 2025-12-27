package com.luna.budgetapp.data.mapper

import com.luna.budgetapp.data.local.entity.ExpenseCache
import com.luna.budgetapp.data.remote.dto.ExpenseDto
import com.luna.budgetapp.domain.model.Expense

fun ExpenseDto.toCache(): ExpenseCache {
    return ExpenseCache(
        id = this.id,
        name = this.name,
        amount = this.amount,
        category = this.category,
        type = this.type,
        date = this.date
    )
}

fun ExpenseCache.toModel(): Expense {
    return Expense(
        id = this.id,
        name = this.name,
        amount = this.amount,
        category = this.category,
        type = this.type,
        date = this.date
    )
}

fun Expense.toCache(): ExpenseCache {
    return ExpenseCache(
        id = this.id,
        name = this.name,
        amount = this.amount,
        category = this.category,
        type = this.type,
        date = this.date
    )
}

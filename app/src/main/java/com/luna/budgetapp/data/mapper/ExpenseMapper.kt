package com.luna.budgetapp.data.mapper

import com.luna.budgetapp.data.local.entity.ExpenseEntity
import com.luna.budgetapp.data.remote.dto.ExpenseDto
import com.luna.budgetapp.domain.model.Expense

fun ExpenseDto.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = this.id,
        name = this.name,
        amount = this.amount,
        category = this.category,
        type = this.type,
        date = this.date
    )
}

fun ExpenseEntity.toModel(): Expense {
    return Expense(
        id = this.id,
        name = this.name,
        amount = this.amount,
        category = this.category,
        type = this.type,
        date = this.date
    )
}

fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = this.id,
        name = this.name,
        amount = this.amount,
        category = this.category,
        type = this.type,
        date = this.date
    )
}

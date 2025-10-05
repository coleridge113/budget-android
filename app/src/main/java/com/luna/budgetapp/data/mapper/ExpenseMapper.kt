package com.luna.budgetapp.data.mapper

import com.luna.budgetapp.data.local.entity.ExpenseCache
import com.luna.budgetapp.data.remote.dto.ExpenseDto
import com.luna.budgetapp.domain.Expense

fun ExpenseDto.toExpense(): Expense {
    return Expense(
        id = this.id,
        name = this.name,
        amount = this.amount,
        type = this.type,
        category = this.category,
        date = this.date,
    )
}

fun Expense.toExpenseDto(): ExpenseDto {
    return ExpenseDto(
        id = this.id,
        name = this.name,
        amount =  this.amount,
        type = this.type,
        category = this.category,
        date =  this.date
    )
}

fun Expense.toExpenseCache(): ExpenseCache {
    return ExpenseCache(
        id = this.id,
        name = this.name,
        amount = this.amount,
        type = this.type,
        category =  this.category,
        date =  this.date
    )
}

fun ExpenseCache.toExpense(): Expense {
    return Expense(
        id = this.id,
        name = this.name,
        amount = this.amount,
        type = this.type,
        category =  this.category,
        date =  this.date
    )
}

fun ExpenseDto.toExpenseCache(): ExpenseCache {
    return ExpenseCache(
        id = this.id,
        name = this.name,
        amount = this.amount,
        type = this.type,
        category =  this.category,
        date =  this.date
    )
}

fun ExpenseCache.toExpenseDto(): ExpenseDto {
    return ExpenseDto(
        id = this.id,
        name = this.name,
        amount = this.amount,
        type = this.type,
        category =  this.category,
        date =  this.date
    )
}
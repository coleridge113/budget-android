package com.luna.budgetapp.data.mapper

import com.luna.budgetapp.data.local.entity.ExpenseCache
import com.luna.budgetapp.data.remote.dto.ExpenseDto
import com.luna.budgetapp.domain.model.Expense

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

fun List<ExpenseCache>.toExpenseDto(): List<ExpenseDto> {
    return this.map {
        ExpenseDto(
            id = it.id,
            name = it.name,
            amount = it.amount,
            type = it.type,
            category =  it.category,
            date =  it.date
        )
    }
}

fun List<ExpenseDto>.toExpenseCache(): List<ExpenseCache> {
    return this.map {
        ExpenseCache(
            id = it.id,
            name = it.name,
            amount = it.amount,
            type = it.type,
            category =  it.category,
            date =  it.date
        )
    }
}

fun List<ExpenseDto>.toExpense(): List<Expense> {
    return this.map {
        Expense(
            id = it.id,
            name = it.name,
            amount = it.amount,
            type = it.type,
            category =  it.category,
            date =  it.date
        )
    }
}

fun List<ExpenseCache>.toExpense(): List<Expense> {
    return this.map {
        Expense(
            id = it.id,
            name = it.name,
            amount = it.amount,
            type = it.type,
            category =  it.category,
            date =  it.date
        )
    }
}
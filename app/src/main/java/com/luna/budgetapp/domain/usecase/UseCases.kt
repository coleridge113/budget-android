package com.luna.budgetapp.domain.usecase

import com.luna.budgetapp.domain.usecase.expense.AddExpenseUseCase
import com.luna.budgetapp.domain.usecase.expense.DeleteExpenseUseCase
import com.luna.budgetapp.domain.usecase.expense.GetAllExpensesUseCase
import com.luna.budgetapp.domain.usecase.expense.GetExpensesByCategoryUseCase
import com.luna.budgetapp.domain.usecase.expense.GetExpensesByTypeUseCase
import com.luna.budgetapp.domain.usecase.expense.GetExpensesByDateRangeUseCase
import com.luna.budgetapp.domain.usecase.auth.GetTokenUseCase
import com.luna.budgetapp.domain.usecase.expensepreset.AddExpensePresetUseCase
import com.luna.budgetapp.domain.usecase.expensepreset.DeleteExpensePresetUseCase

data class UseCases(
    val addExpenseUseCase: AddExpenseUseCase,
    val deleteExpenseUseCase: DeleteExpenseUseCase,
    val getAllExpensesUseCase: GetAllExpensesUseCase,
    val getExpensesByCategoryUseCase: GetExpensesByCategoryUseCase,
    val getExpensesByDateRangeUseCase: GetExpensesByDateRangeUseCase,
    val getExpensesByTypeUseCase: GetExpensesByTypeUseCase,
    val getTokenUseCase: GetTokenUseCase,
    val addExpensePresetUseCase: AddExpensePresetUseCase,
    val deleteExpensePresetUseCase: DeleteExpensePresetUseCase,
)

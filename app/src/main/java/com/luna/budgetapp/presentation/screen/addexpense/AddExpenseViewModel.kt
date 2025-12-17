package com.luna.budgetapp.presentation.screen.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.domain.usecase.expense.AddExpenseUseCase
import com.luna.budgetapp.domain.usecase.expense.GetAllExpensesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import android.util.Log

class AddExpenseViewModel(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getAllExpensesUseCase: GetAllExpensesUseCase
): ViewModel() {
    private val _state = MutableSharedFlow<AddExpenseState>()
    val state = _state.asSharedFlow()

    fun getAllExpenses() {
        viewModelScope.launch {
            getAllExpensesUseCase().collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        _state.emit(AddExpenseState.GetExpenses(resource.data ?: emptyList()))
                        Log.d("ExpenseViewModel", "expenses: ${resource.data}")
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}
                }
            }
        }
    }
}

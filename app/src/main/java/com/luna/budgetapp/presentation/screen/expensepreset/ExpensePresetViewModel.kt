package com.luna.budgetapp.presentation.screen.expensepreset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.domain.usecase.UseCases
import com.luna.budgetapp.presentation.screen.expensepreset.ViewModelStateEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpensePresetViewModel(
    private val useCases: UseCases,
    private val pusherManager: PusherManager,
    private val expensePresetRepo: ExpensePresetRepository,
    private val expenseRepo: ExpenseRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ViewModelStateEvents.UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ViewModelStateEvents.Event) {
        when (event) {
            ViewModelStateEvents.Event.AddExpensePreset -> emitShowDialog()
            ViewModelStateEvents.Event.CycleDateFilter -> {}
            ViewModelStateEvents.Event.DismissDialog -> emitDismissDialog()
            is ViewModelStateEvents.Event.ConfirmDialog -> { 
                val state = _uiState.value
                if (!state.isDialogVisible || state.isSaving) return

                val expensePreset = ExpensePreset(
                    amount = event.amount.toDouble(),
                    category = event.category,
                    type = event.type
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        isSaving = true
                    )
                }

                viewModelScope.launch {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isDialogVisible = false,
                            isSaving = false,
                            expensePresets = currentState.expensePresets + expensePreset
                        )
                    }
                }
            }
            is ViewModelStateEvents.Event.AddExpense -> { 
                viewModelScope.launch {
                    val expense = Expense(
                        category = event.expensePreset.category,
                        type = event.expensePreset.type,
                        amount = event.expensePreset.amount
                    )
                    // expenseRepo.addExpense(expense)
                    
                    _uiState.update { currentState ->
                        currentState.copy(
                            expenses = currentState.expenses + expense,
                            totalAmount = currentState.totalAmount + expense.amount
                        )
                    }
                }
            }
        }
    }

    private fun emitShowDialog() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isDialogVisible = true
                )
            }
        }
    }

    private fun emitDismissDialog() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isDialogVisible = false
                )
            }
        }
    }
}

object ViewModelStateEvents {
    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val expensePresets: List<ExpensePreset> = emptyList(),
        val expenses: List<Expense> = emptyList(),
        val isSaving: Boolean = false,
        val isDialogVisible: Boolean = false,
        val totalAmount: Double = 0.0,
        val dateFilter: String = "",
    )

    sealed interface Event {
        data object AddExpensePreset : Event
        data object DismissDialog : Event
        data object CycleDateFilter : Event
        data class ConfirmDialog(val category: String, val type: String, val amount: String) : Event
        data class AddExpense(val expensePreset: ExpensePreset) : Event
    }

    sealed class Navigation {
    }
}

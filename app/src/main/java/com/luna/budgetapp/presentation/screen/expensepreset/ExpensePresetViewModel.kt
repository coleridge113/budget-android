package com.luna.budgetapp.presentation.screen.expensepreset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.domain.usecase.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import android.util.Log

class ExpensePresetViewModel(
    private val useCases: UseCases,
    private val pusherManager: PusherManager,
    private val expensePresetRepo: ExpensePresetRepository,
    private val expenseRepo: ExpenseRepository
): ViewModel() {

    init {
        observeExpenses()
        observeExpensePresets()
    }

    private val _uiState = MutableStateFlow(ViewModelStateEvents.UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ViewModelStateEvents.Event) {
        when (event) {
            ViewModelStateEvents.Event.AddExpensePreset -> showPresetDialog()
            ViewModelStateEvents.Event.DismissDialog -> dismissPresetDialog()
            ViewModelStateEvents.Event.CycleDateFilter -> {}
            is ViewModelStateEvents.Event.AddExpense -> addExpense(event.expensePreset)
            is ViewModelStateEvents.Event.AddCustomExpense -> addCustomExpense(event.selectedPreset)
            is ViewModelStateEvents.Event.ConfirmDialog -> { 
                saveExpensePreset(event.category, event.type, event.amount) 
            }
        }
    }

    private fun observeExpenses() {
        viewModelScope.launch {
            expenseRepo.getExpensesByDateRange(
                start = LocalDate.now().atStartOfDay(),
                end = LocalDate.now().atTime(LocalTime.MAX)
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                expenses = resource.data,
                                totalAmount = resource.data.sumOf { it.amount }
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(error = resource.message)
                        }
                    }
                }
            }
        }
    }

    private fun observeExpensePresets() {
        viewModelScope.launch {
            expensePresetRepo.getAllExpensePresets().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                expensePresets = resource.data
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Error -> {
                        Log.e("ExpensePreset", "Failed to get resource: ${resource.message}")
                        _uiState.update {
                            it.copy(error = resource.message)
                        }
                    }
                }
            }
        }
    }

    private fun showPresetDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                isDialogVisible = true
            )
        }
    }

    private fun dismissPresetDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                isDialogVisible = false
            )
        }
    }

    private fun saveExpensePreset(category: String, type: String, amount: String) {
        val state = _uiState.value
        if (!state.isDialogVisible || state.isSaving) return

        val expensePreset = ExpensePreset(
            amount = amount.toDoubleOrNull() ?: 0.0,
            category = category,
            type = type.ifEmpty { category }
        )

        _uiState.update { currentState ->
            currentState.copy(
                isSaving = true
            )
        }

        viewModelScope.launch {
            try {
                expensePresetRepo.addExpensePreset(expensePreset)
                _uiState.update { currentState ->
                    currentState.copy(
                        isDialogVisible = false,
                        isSaving = false,
                        selectedPreset = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isSaving = false,
                        error = "Failed to save Expense Preset..."
                    )
                }
            }
        }
    }

    private fun addExpense(expensePreset: ExpensePreset) {
        viewModelScope.launch {
            val expense = Expense(
                category = expensePreset.category,
                type = expensePreset.type,
                amount = expensePreset.amount
            )
            expenseRepo.addExpense(expense)

            _uiState.update { currentState ->
                currentState.copy(
                    totalAmount = currentState.totalAmount + expense.amount
                )
            }
        }
    }

    private fun addCustomExpense(selectedPreset: ExpensePreset) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedPreset = selectedPreset,
                isDialogVisible = true
            )
        }
    }

    private fun deleteExpense(expenseId: Long) {
        viewModelScope.launch {
            expenseRepo.deleteExpense(expenseId)
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
        val selectedPreset: ExpensePreset? = null,
        val totalAmount: Double = 0.0,
        val dateFilter: String = "",
    )

    sealed interface Event {
        data object AddExpensePreset : Event
        data object DismissDialog : Event
        data object CycleDateFilter : Event
        data class ConfirmDialog(val category: String, val type: String, val amount: String) : Event
        data class AddExpense(val expensePreset: ExpensePreset) : Event
        data class AddCustomExpense(val selectedPreset: ExpensePreset) : Event
        data class DeleteExpense(val expenseId: Long) : Event
    }

    sealed class Navigation {
    }
}

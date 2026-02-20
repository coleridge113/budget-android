package com.luna.budgetapp.presentation.screen.expensepreset

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.domain.usecase.UseCases
import com.luna.budgetapp.presentation.model.DateFilter
import com.luna.budgetapp.presentation.model.resolve
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ExpensePresetViewModel(
    private val useCases: UseCases,
    private val pusherManager: PusherManager,
    private val expensePresetRepo: ExpensePresetRepository,
    private val expenseRepo: ExpenseRepository
): ViewModel() {

    private val selectedRange = MutableStateFlow(DateFilter.DAILY)

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeExpenses()
        observeExpensePresets()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.DismissDialog -> dismissDialog()
            is Event.AddExpense -> addExpense(event.expensePreset)
            is Event.ShowExpenseForm -> showExpenseForm(event.selectedPreset)
            is Event.ShowConfirmationDialog -> showPresetDeleteConfirmationDialog(event.expensePresetId)
            is Event.AddCustomExpense -> showExpenseForm(event.selectedPreset)
            is Event.DeleteExpense -> deleteExpense(event.expenseId)
            is Event.DeleteExpensePreset -> deleteExpensePreset(event.expensePresetId)
            is Event.ConfirmDialog -> saveExpensePreset(event.category, event.type, event.amount)
            is Event.SelectDateRange -> selectDateRange(event.selectedRange)
        }
    }

    private fun observeExpenses() {
        viewModelScope.launch {
            selectedRange
                .flatMapLatest { range ->
                    val (start, end) = range.resolve()

                    useCases.getExpensesByDateRangeUseCase(start, end)
                        .onStart {
                            _uiState.update { it.copy(isExpensesLoading = true) }
                        }
                }
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isExpensesLoading = false,
                            error = error.localizedMessage
                        )
                    }
                }
                .collect { expenses ->
                    _uiState.update {
                        it.copy(
                            isExpensesLoading = false,
                            error = null,
                            expenses = expenses
                        )
                    }
                }
        }
    }

    private fun observeExpensePresets() {
        viewModelScope.launch {
            useCases.getAllExpensePresetsUseCase()
                .onStart {
                    _uiState.update {
                        it.copy(
                            isPresetsLoading = true,
                        )
                    }
                }
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isPresetsLoading = false,
                            error = error.localizedMessage
                        )
                    }
                }
                .collect { expensePresets ->
                    _uiState.update {
                        it.copy(
                            isPresetsLoading = false,
                            error = null,
                            expensePresets = expensePresets
                        )
                    }
                }
        }
    }

    private fun showExpenseForm(selectedPreset: ExpensePreset?) {
        _uiState.update { currentState ->
            currentState.copy(
                dialogState = DialogState.ExpenseForm(
                    selectedPreset = selectedPreset,
                    isSaving = false
                )
            )
        }
    }

    private fun dismissDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                 dialogState = null
            )
        }
    }

    private fun saveExpensePreset(category: String, type: String, amount: String) {
        val dialog = _uiState.value.dialogState

        if (dialog !is DialogState.ExpenseForm || dialog.isSaving) return 

        val expensePreset = ExpensePreset(
            amount = amount.toDoubleOrNull() ?: 0.0,
            category = category,
            type = type.ifEmpty { category }
        )

        _uiState.update { currentState ->
            currentState.copy(
                 dialogState = dialog.copy(isSaving = true)
            )
        }

        viewModelScope.launch {
            try {
                useCases.addExpensePresetUseCase(expensePreset)
                _uiState.update {
                    it.copy(
                        dialogState = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        dialogState = dialog.copy(isSaving = false),
                        error = "Error saving preset..."
                    )
                }
            }
        }
    }

    private fun addExpense(expensePreset: ExpensePreset) {
        val state = uiState.value

        viewModelScope.launch {
            val expense = Expense(
                category = expensePreset.category,
                type = expensePreset.type,
                amount = expensePreset.amount
            )
            expenseRepo.addExpense(expense)
            
            if (state.dialogState != null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        dialogState = null
                    )
                }
            }
        }
    }

    private fun deleteExpense(expenseId: Long) {
        viewModelScope.launch {
            useCases.deleteExpenseUseCase(expenseId)
        }
    }

    private fun deleteExpensePreset(expensePresetId: Long) {
        viewModelScope.launch {
            try {
                useCases.deleteExpensePresetUseCase(expensePresetId)
                _uiState.update { currentState ->
                    currentState.copy(
                        dialogState = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        error = "Failed to delete expense preset..."
                    )
                }
            }
        }
    }

    private fun showPresetDeleteConfirmationDialog(expensePresetId: Long) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    dialogState = DialogState.ConfirmDeleteExpensePreset(expensePresetId)
                )
            }
        }
    }

    private fun selectDateRange(selectedRange: DateFilter) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    selectedRange = selectedRange
                )
            }
        }
    }
}

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

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: Event) {
        when (event) {
            Event.AddExpensePreset -> showAddPresetDialog()
            Event.DismissDialog -> dismissPresetDialog()
            Event.CycleDateFilter -> {}
            is Event.AddExpense -> addExpense(event.expensePreset)
            is Event.AddCustomExpense -> addCustomExpense(event.selectedPreset)
            is Event.DeleteExpense -> deleteExpense(event.expenseId)
            is Event.DeleteExpensePreset -> deleteExpensePreset(event.expensePresetId)
            is Event.ConfirmDialog -> { 
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
                                isExpensesLoading = false,
                                expenses = resource.data,
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isExpensesLoading = true)
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
                                isPresetsLoading = false,
                                expensePresets = resource.data
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isPresetsLoading = true)
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

    private fun showAddPresetDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                dialogState = DialogState.ExpenseForm(
                    selectedPreset = null,
                    isSaving = false
                )
            )
        }
    }

    private fun dismissPresetDialog() {
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
        viewModelScope.launch {
            val expense = Expense(
                category = expensePreset.category,
                type = expensePreset.type,
                amount = expensePreset.amount
            )
            expenseRepo.addExpense(expense)
        }
    }

    private fun addCustomExpense(selectedPreset: ExpensePreset) {
        _uiState.update { currentState ->
            currentState.copy(
                dialogState = DialogState.ExpenseForm(
                    selectedPreset = selectedPreset,
                    isSaving = false
                )
            )
        }
    }

    private fun deleteExpense(expenseId: Long) {
        viewModelScope.launch {
            useCases.deleteExpenseUseCase(expenseId)
        }
    }

    private fun deleteExpensePreset(expensePresetId: Long) {
        viewModelScope.launch {
            useCases.deleteExpensePresetUseCase(expensePresetId)
        }
    }
}
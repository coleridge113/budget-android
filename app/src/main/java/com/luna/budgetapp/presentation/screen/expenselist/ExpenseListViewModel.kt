package com.luna.budgetapp.presentation.screen.expenselist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.domain.usecase.UseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ExpenseListViewModel(
    private val useCases: UseCases
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeExpenses()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.Foo -> {}
        }
    }

    private fun observeExpenses() {
        viewModelScope.launch {
            _uiState
                .map { it.selectedRange }
                .distinctUntilChanged()
                .flatMapLatest {
                    useCases.getAllExpensesUseCase()
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
                    _uiState.update { currentState ->
                        currentState.copy(
                            isExpensesLoading = false,
                            error = null,
                            expenses = expenses.sortedByDescending { it.id }
                        )
                    }
                }
        }
    }
}

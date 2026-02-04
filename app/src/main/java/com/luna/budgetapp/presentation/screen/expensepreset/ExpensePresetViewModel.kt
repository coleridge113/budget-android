package com.luna.budgetapp.presentation.screen.expensepreset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.domain.usecase.UseCases
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpensePresetViewModel(
    private val useCases: UseCases,
    private val pusherManager: PusherManager,
    private val repository: ExpensePresetRepository
): ViewModel() {

    init {
        initializePusher()
    }

    private val _uiState = MutableStateFlow(ViewModelStateEvents.UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ViewModelStateEvents.Event) {
        when (event) {
            ViewModelStateEvents.Event.LoadTable -> {}
            ViewModelStateEvents.Event.AddExpensePreset -> addExpensePreset()
        }
    }

    private fun addExpensePreset() {
        val expensePreset = ExpensePreset(
            id = 4L,
            amount = 15.99,
            category = "Streaming",
            type = "Entertainment"
        )
        _uiState.update { currentState ->
            currentState.copy(
                success = currentState.success + expensePreset
            )
        }
    }

    private fun initializePusher() {
        viewModelScope.launch {
            pusherManager.initPusher()
            pusherManager.subscribeToExpenseChannel {}
        }
    }
}

object ViewModelStateEvents {
    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val success: List<ExpensePreset> = emptyList()
    )

    sealed interface Event {
        data object LoadTable : Event
        data object AddExpensePreset : Event
    }

    sealed class Navigation {
    }
}
